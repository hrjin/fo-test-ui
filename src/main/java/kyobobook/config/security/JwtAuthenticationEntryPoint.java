/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 17.
 *
 ****************************************************/
package kyobobook.config.security;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.Collections;
import java.util.Enumeration;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import kyobobook.common.Constants;
import kyobobook.common.ErrorCode;
import kyobobook.utils.CookieUtil;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtAuthenticationEntryPoint.java
 * @Date        : 2021. 9. 17.
 * @author      : smlee1@kyobobook.com
 * @description : 인증 되지 않은 사용자가 인증이 필요한 API 호출시 권한이 없는 경우 실행.
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    
    private final CookieUtil cookieUtil;
    
    public JwtAuthenticationEntryPoint(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException authException) throws IOException, ServletException {
        
        int errorType = 0;
        if(request.getAttribute("errorType") != null) {
            errorType = (int) request.getAttribute("errorType");
        }
        
        String refreshParam = "";
        if(ErrorCode.MalformedJwtException.getCode() == errorType) {
            // 서명 오류
        } else if(ErrorCode.ExpiredJwtException.getCode() == errorType) {
            // 토큰 만료
            refreshParam = "?isRefresh=true";
        } else if(ErrorCode.UnsupportedJwtException.getCode() == errorType) {
            // 지원하지 않는 토큰
        } else if(ErrorCode.IllegalArgumentException.getCode() == errorType) {
            //토큰 정보 오류
        } else if(ErrorCode.NoSuchAlgorithmException.getCode() == errorType) {
            // 토큰 파싱 오류
        } else if(ErrorCode.InvalidKeySpecException.getCode() == errorType) {
         // 토큰 파싱 오류
        }
        
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enumeration = request.getParameterNames();
        Collections.list(enumeration).stream().forEach(key -> {
            if(sb.length() > 0) sb.append("&"); 
            sb.append(key + "=" + request.getParameter(key));
        });
        
        // 쿠키에 담고 만료시간을 10분으로 설정
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_PARAMS, URLEncoder.encode(sb.toString(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10, response);
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_URI, URLEncoder.encode(request.getRequestURI(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10, response);
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_METHOD, URLEncoder.encode(request.getMethod(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10, response);
        
        //request.getRequestDispatcher("/api/auth/reissue").forward(request, response);

        response.sendRedirect("/login");
    }
}
