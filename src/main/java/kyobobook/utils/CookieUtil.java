/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 27.
 *
 ****************************************************/
package kyobobook.utils;

import java.net.URLDecoder;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import com.google.common.net.InternetDomainName;

/**
 * @Project     : bo-prototype-ui
 * @FileName    : CookieUtil.java
 * @Date        : 2021. 9. 27.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
@Component
public class CookieUtil {

//    private HttpServletRequest request;
//    private HttpServletResponse response;
    
    public static final int EXPIRY_MINUTE = 60;
    public static final int EXPIRY_HOUR = EXPIRY_MINUTE * 60;
    public static final int EXPIRY_DAY = EXPIRY_HOUR * 24;
    
    
    public CookieUtil() {
//        ServletRequestAttributes servletContainer = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
//        request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();

//        request = servletContainer.getRequest();
//        response = servletContainer.getResponse();
    }
    
    public void setCookie(String key, String value, int expiry, HttpServletResponse response) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
//        String domain = InternetDomainName.from(request.getServerName()).topPrivateDomain().toString();
        String domain = "";
        ResponseCookie responseCookie = ResponseCookie.from(key, value)
                .domain(domain)
//                .sameSite("None")
//                .secure(false)
                .path("/")
                .maxAge(expiry)
                .build();
        response.addHeader("Set-Cookie", responseCookie.toString());
    }
    
    /**
     * @Method      : setCookie
     * @Date        : 2021. 9. 27.
     * @author      : smlee1@kyobobook.com
     * @description : 쿠키를 생성한다
     * @param key
     * @param value
     * @param day
     */
    public void setCookie(String key, String value, int expiry) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getResponse();
        this.setCookie(key, value, expiry, response);
    }
    
    /**
     * @Method      : getCookie
     * @Date        : 2021. 9. 27.
     * @author      : smlee1@kyobobook.com
     * @description : 쿠키의 값을 조회 한다
     * @param key
     * @return
     */
    public String getCookie(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest(); 
        String value = null;
        Cookie[] cookies = request.getCookies();
        try {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(key)) {
                    value = URLDecoder.decode(cookie.getValue(), "utf-8");
                }
            }
        } catch (Exception e) {
            value = null;
        }
        return value;
    }

    /**
     * @Method      : delCookie
     * @Date        : 2021. 9. 27.
     * @author      : smlee1@kyobobook.com
     * @description : 쿠키의 값을 삭제한다.
     * @param key
     */
    public void delCookie(String key) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder .getRequestAttributes()).getResponse();
        String domain = InternetDomainName.from(request.getServerName()).topPrivateDomain().toString();
        Cookie cookie = new Cookie(key, null);
        cookie.setValue("");
        cookie.setDomain(domain);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }
}
