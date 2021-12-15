/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 15.
 *
 ****************************************************/
package kyobobook.config.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import kyobobook.common.Constants;
import kyobobook.common.ErrorCode;
import kyobobook.config.security.jwt.keys.JwtKeyManager;
import kyobobook.config.security.jwt.keys.JwtKeys;
import kyobobook.utils.CookieUtil;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtProvider.java
 * @Date        : 2021. 9. 15.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
@Component
public class JwtProvider {

    private static final Logger logger = LoggerFactory.getLogger(JwtProvider.class);
    
    private static final String AUTHORITIES_KEY = "roles";
    
    private final JwtKeyManager jwtKeyManager;
    private final CookieUtil cookieUtil;
    
    @Value("${resource.api.auth.publicKey}")
    private String publicKey;
    
    public JwtProvider(@Qualifier("jwtKeyRSA") JwtKeyManager jwtKeyManager
            , CookieUtil cookieUtil) throws Exception {
        this.jwtKeyManager = jwtKeyManager;
        this.cookieUtil = cookieUtil;
    }

    /**
     * @Method      : parseClaims
     * @Date        : 2021. 10. 12.
     * @author      : smlee1@kyobobook.com
     * @description : Token 을 파싱해서 Claims 로 반환한다.
     * @param accessToken
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private Claims parseClaims(String accessToken) throws NoSuchAlgorithmException, InvalidKeySpecException {
        // JwtKeys jwtKeys = jwtKeyManager.getKeys(Constants.PUBLIC_KEY);
        JwtKeys jwtKeys = jwtKeyManager.getKeys(publicKey);
        return Jwts.parser()
                .setSigningKey(jwtKeys.getPublicKey())
                .parseClaimsJws(accessToken)
                .getBody();
    }

    /**
     * @Method      : getAuthentication
     * @Date        : 2021. 10. 12.
     * @author      : smlee1@kyobobook.com
     * @description : JWT Token 으로 회원 인증 정보 조회
     * @param accessToken
     * @return
     * @throws Exception
     */
    public Authentication getAuthentication(String accessToken) throws Exception {
        Claims claims = parseClaims(accessToken);
        
        String list = claims.get(AUTHORITIES_KEY).toString().replace("[", "").replace("]", "");
        
        Collection<? extends GrantedAuthority> authorities = Arrays.stream(list.split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        logger.info("authority ::: " + authorities.toString());
        UserDetails userDetails = new User(claims.getSubject(), "", authorities);
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    /**
     * @Method      : resolveToken
     * @Date        : 2021. 10. 12.
     * @author      : smlee1@kyobobook.com
     * @description : 쿠키에서 토큰 정보를 조회 한다. 
     * @param request
     * @return
     */
    public String resolveToken(HttpServletRequest servletRequest, String kind) {
        String token = null;
        
        if(Constants.COOKIE_ACCESS_TOKEN.equals(kind)) {
            token = cookieUtil.getCookie(Constants.COOKIE_ACCESS_TOKEN);
        } else {
            token = cookieUtil.getCookie(Constants.COOKIE_REFRESH_TOKEN);
        }
        
        logger.info("token ::: " + token);
        
        if (StringUtils.hasText(token)) {
            if(token.startsWith(Constants.BEARER_PREFIX)) {
                return token.substring(7);
            } else {
                return token;
            }
        }
        
        return null;
    }
    

    /**
     * @Method      : validateToken
     * @Date        : 2021. 10. 12.
     * @author      : smlee1@kyobobook.com
     * @description : 토큰의 유효성 + 만료일자 확인
     * @param accessToken
     * @param request
     * @return
     */
    public boolean validateToken(String accessToken, HttpServletRequest request) {
        try {
            Claims claims = parseClaims(accessToken);
            logger.info("claims.getExpiration() :: " + claims.getExpiration());
            
            return !claims.getExpiration().before(new Date());
        } catch (MalformedJwtException e) {
            // 잘못된 Token 서명입니다.
            request.setAttribute("errorType", ErrorCode.MalformedJwtException.getCode());
        } catch (ExpiredJwtException e) {
            // 만료된 토큰입니다.
            request.setAttribute("errorType", ErrorCode.ExpiredJwtException.getCode());
        } catch (UnsupportedJwtException e) {
            // 지원되지 않는 토큰입니다.
            request.setAttribute("errorType", ErrorCode.UnsupportedJwtException.getCode());
        } catch (IllegalArgumentException e) {
            // 토큰 정보가 잘못되었습니다.
            request.setAttribute("errorType", ErrorCode.IllegalArgumentException.getCode());
        } catch (NoSuchAlgorithmException e) {
            request.setAttribute("errorType", ErrorCode.NoSuchAlgorithmException.getCode());
        } catch(InvalidKeySpecException e) {
            request.setAttribute("errorType", ErrorCode.InvalidKeySpecException.getCode());
        }
        
        return false;
    }
}
