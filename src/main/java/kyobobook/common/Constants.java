/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 24.
 *
 ****************************************************/
package kyobobook.common;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : Constants.java
 * @Date        : 2021. 9. 24.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
public class Constants {
    public static String PUBLIC_KEY = "";
    /*** system property 의 jwt용 public key 를 조회하기 위한 key 값 */
    public static final String PROPS_PUBLIC_KEY_NAME = "AUTH_PUBLIC_KEY";
    
    public static final String BEARER_PREFIX = "Bearer ";
    public static final String AUTHORIZATION_HEADER = "Authorization";
    //public static final String REFRESH_HEADER = "kb-Refresh";
    
    public static final String COOKIE_ACCESS_TOKEN = "kb-access-token";
    public static final String COOKIE_REFRESH_TOKEN = "kb-refresh-token";
    
    public static final String COOKIE_NAME_RETURN_PARAMS = "auth-return-params";
    public static final String COOKIE_NAME_RETURN_URI = "auth-return-uri";
    public static final String COOKIE_NAME_RETURN_METHOD = "auth-return-method";
}
