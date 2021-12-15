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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;
import kyobobook.common.Constants;
import kyobobook.utils.CookieUtil;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtAccessDeniedHandler.java
 * @Date        : 2021. 9. 17.
 * @author      : smlee1@kyobobook.com
 * @description : Access 권한이 없는 페이지에 접속 했을때 
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {

    private final CookieUtil cookieUtil;
    
    public JwtAccessDeniedHandler(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }
    
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
            AccessDeniedException accessDeniedException) throws IOException, ServletException {
        
        StringBuilder sb = new StringBuilder();
        Enumeration<String> enumeration = request.getParameterNames();
        Collections.list(enumeration).stream().forEach(key -> {
            if(sb.length() > 0) sb.append("&"); 
            sb.append(key + "=" + request.getParameter(key));
        });
        
        // 쿠키에 담고 만료시간을 10분으로 설정
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_PARAMS, URLEncoder.encode(sb.toString(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10);
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_URI, URLEncoder.encode(request.getRequestURI(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10);
        cookieUtil.setCookie(Constants.COOKIE_NAME_RETURN_METHOD, URLEncoder.encode(request.getMethod(), "UTF-8"), CookieUtil.EXPIRY_MINUTE * 10);
        
        request.setAttribute("isRefresh", true);
        request.getRequestDispatcher("/auth/login?isRefresh=true").forward(request, response);
    }
}
