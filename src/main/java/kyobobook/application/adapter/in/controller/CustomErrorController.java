/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 12. 1.
 *
 ****************************************************/
package kyobobook.application.adapter.in.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : CustomErrorController.java
 * @Date        : 2021. 12. 1.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Controller
public class CustomErrorController implements ErrorController {
    private static final Logger logger = LoggerFactory.getLogger(CustomErrorController.class);
    private static final String VIEW_URL = "/view/";

    @ModelAttribute("cdnURI")
    public String cdnURI (
            @Value("${cloud.aws.cdn.host}") String host,
            @Value("${cloud.aws.cdn.service.resFo}") String url) {
        // inject via application.properties
        return host + url;
    }
    
    @Override
    public String getErrorPath() {
        return "/error";
    }

    @GetMapping("/error")
    public ModelAndView handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        
        String reqUrl = VIEW_URL + status.toString();

        ModelAndView mv = new ModelAndView();
        mv.setViewName(reqUrl);

        return mv;
    }
}
