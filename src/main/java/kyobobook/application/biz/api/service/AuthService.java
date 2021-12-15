/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 30.
 *
 ****************************************************/
package kyobobook.application.biz.api.service;

import java.net.URLEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import kyobobook.application.biz.api.port.in.AuthPort;
import kyobobook.application.domain.token.Tokens;
import kyobobook.common.Constants;
import kyobobook.exception.BizRuntimeException;
import kyobobook.utils.CookieUtil;
import lombok.RequiredArgsConstructor;
// import reactor.core.publisher.Mono;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : VeryfiyService.java
 * @Date        : 2021. 11. 29.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Service
@RequiredArgsConstructor
public class AuthService implements AuthPort{
    private static final Logger logger = LoggerFactory.getLogger(OnkService.class);
    
    @Value("${resource.api.auth.host}")
    private String authServerUrl;
    
    private final WebClient webClient;
    private final CookieUtil cookieUtil;
    
    @Override
    public <T> T getAccessToken(Tokens tokens) {
        logger.info("rest api to auth server...");
        ResponseEntity res = null;
        String newAccessToken = "";
        
        try {
                res = webClient.mutate()
                    .build()
                    .get()
                    .uri(authServerUrl)
                    .header(Constants.AUTHORIZATION_HEADER, tokens.getRefreshToken())
                    .exchange()
                    .flatMap(response -> response.bodyToMono(ResponseEntity.class))
                    .block();
                
                newAccessToken = res.getHeaders().get(Constants.AUTHORIZATION_HEADER).get(0);
                
                // 새로운 access token 을 cookie에 set 해준다.
                cookieUtil.setCookie(Constants.COOKIE_ACCESS_TOKEN, URLEncoder.encode(newAccessToken, "UTF-8"), 60 * 60);
        } catch (Exception e) {
            throw new BizRuntimeException(e.getMessage());
        }
        
        return (T) res;
    }
    
}
