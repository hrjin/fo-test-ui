/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 12.
 *
 ****************************************************/
package kyobobook.common;

import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Project     : fo-demo-ui
 * @FileName    : CommonUtils.java
 * @Date        : 2021. 11. 12.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
public class CommonUtils {
    private static final Logger LOGGER = LoggerFactory.getLogger(CommonUtils.class);
    
    private static final String VIEW_ROOT = "view/";

    public static Object viewPathResolve;
    
    /**
     * LOGGER 개행문자 제거 (Object)
     *
     * @param obj
     * @return String the replaced string
     */
    public static String loggerReplace(Object obj) {
        return obj.toString().replaceAll("[\r\n]","");
    }

    /**
     * LOGGER 개행문자 제거 (String)
     *
     * @param str
     * @return String the replaced string
     */
    public static String loggerReplace(String str) {
        return str.replaceAll("[\r\n]","");
    }
    
    
    /**
     * client가 접근한 device 판별
     * 
     * @Method      : isMobile
     * @Date        : 2021. 11. 12.
     * @author      : hrjin@kyobobook.com
     * @description : client가 접근한 device 판별
     * @param request
     * @return boolean
     */
    public static boolean isMobile(HttpServletRequest request) {
        String userAgent = request.getHeader("user-agent");
        boolean mobile1 = userAgent.matches( ".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
        boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");
        
        LOGGER.info("mobile1 true? " + mobile1);
        LOGGER.info("mobile2 true? " + mobile2);

        if (mobile1 || mobile2) {
            return true;
        }
        return false;
    }
    
    /**
     * View 디렉토리 경로 Resolver
     * 
     * @Method      : viewPathResolve
     * @Date        : 2021. 11. 18.
     * @author      : eykim@kyobobook.com
     * @param subPath 하위 경로
     * @return /view/*
     */
    public static String viewPathResolve(String subPath) {
        if (subPath.charAt(0) == '/') {
            subPath = subPath.substring(1);
        }
        if (subPath.charAt(subPath.length() - 1) == '/') {
            subPath += "index";
        }
        return VIEW_ROOT + subPath;
    }
    
    /**
     * View 디렉토리 경로 Resolver
     * 
     * @Method      : viewPathResolve
     * @Date        : 2021. 11. 18.
     * @author      : eykim@kyobobook.com
     * @param subPath 하위 경로
     * @param device ink|mok
     * @return /view/*
     */
    public static String viewPathResolve(String subPath, String device) {
        if (subPath.charAt(0) != '/') {
            device += '/';
        }
        return viewPathResolve(device + subPath);
    }
    
    /**
     * View 디렉토리 경로 Resolver
     * 
     * @Method      : viewPathResolve
     * @Date        : 2021. 11. 18.
     * @author      : eykim@kyobobook.com
     * @param subPath 하위 경로
     * @param bMobile 모바일 디바이스 여부
     * @return /view/*
     */
    public static String viewPathResolve(String subPath, boolean bMobile) {
        String device = bMobile ? "mok" : "ink";
        return viewPathResolve(subPath, device);
    }
    
    /*
    * public static boolean isMobile() {
    * LOGGER.info("mobile1 true? " + mobile1); LOGGER.info("mobile2 true? " + mobile2);
    * if (mobile1 || mobile2) { return true; } else { return false; } }
    */
}
