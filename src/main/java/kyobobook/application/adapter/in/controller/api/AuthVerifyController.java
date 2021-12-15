/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 25.
 *
 ****************************************************/
package kyobobook.application.adapter.in.controller.api;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.RequestContextUtils;
import kyobobook.application.biz.api.service.AuthService;
import kyobobook.application.domain.token.Tokens;
import kyobobook.common.Constants;
import kyobobook.config.security.JwtProvider;

/**
 * @Project     : fo-product-ui
 * @FileName    : AuthVerifyController.java
 * @Date        : 2021. 11. 25.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@RestController
@RequestMapping("/api/auth")
public class AuthVerifyController {
    private static final Logger logger = LoggerFactory.getLogger(AuthVerifyController.class);
    
    private final AuthService authVerfiyService;
    private final JwtProvider jwtProvider;
    
    
    public AuthVerifyController(AuthService authVerfiyService, JwtProvider jwtProvider) {
        this.authVerfiyService = authVerfiyService;
        this.jwtProvider = jwtProvider;
    }


    @GetMapping("/reissue")
    public void authReissue(HttpServletRequest request) {
        logger.info("reissue in...");
        // Auth Server로 호출
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);
        Tokens tokens = new Tokens();
        
        if(flashMap != null) {
            tokens = (Tokens) flashMap.get("token");
        }

        authVerfiyService.getAccessToken(tokens);
    }
    
    @GetMapping("/verify")
    public String authVerify(HttpServletRequest request, HttpServletResponse response) {
        String bearerToken = request.getHeader(Constants.AUTHORIZATION_HEADER);
        
        logger.info("token ::: " + bearerToken);
        
        if (StringUtils.hasText(bearerToken)) {
            if(bearerToken.startsWith(Constants.BEARER_PREFIX)) {
                bearerToken = bearerToken.substring(7);
            }
        }
        
        try {
            logger.info("true? false? ..." + jwtProvider.validateToken(bearerToken, request));
            
            // 토큰 유효성 검사
            if (StringUtils.hasText(bearerToken) && jwtProvider.validateToken(bearerToken, request)) {
                Authentication authentication = jwtProvider.getAuthentication(bearerToken);
                // SecurityContext 에 Authentication 을 저장
                // 1. 여기서 SecurityContextHodler Authority 'USER' 권한 준다.
                SecurityContextHolder.getContext().setAuthentication(authentication);
                
                // 2. 그 다음 response header에 토큰 값 다시 반환...
                response.setHeader(Constants.AUTHORIZATION_HEADER, bearerToken);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
        
        // 확인용...
        logger.info("response header >>> " + response.getHeader(Constants.AUTHORIZATION_HEADER));
        
        return bearerToken;
    }
    
    
}
