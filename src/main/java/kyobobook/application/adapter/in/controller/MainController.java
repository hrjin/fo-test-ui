/***************************************************
  * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
  * This software is the proprietary information of Kyobo Book.
  *
  * Revision History
  * Author                         Date          Description
  * --------------------------     ----------    ----------------------------------------
  * eykim@kyobobook.com      2021. 11. 15.
  *
  ****************************************************/
package kyobobook.application.adapter.in.controller;

import java.util.Arrays;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import kyobobook.application.domain.device.Device;
import kyobobook.common.CommonUtils;

/**
* @Project     : fo-ui-proto-r2
* @FileName    : MainController.java
* @Date        : 2021. 11. 15.
* @author      : eykim@kyobobook.com
* @description :
*/
@CrossOrigin(origins = "http://localhost:3300")
@Controller
public class MainController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);
    
    @Autowired
    Environment env;
    
    @ModelAttribute("cdnURI")
    public String cdnURI (
            @Value("${cloud.aws.cdn.host}") String host,
            @Value("${cloud.aws.cdn.service.resFo}") String url) {
        // inject via application.properties
        return host + url;
    }
    
    /**
     * Main 화면
     * 
     * @Method      : main
     * @Date        : 2021. 11. 16.
     * @author      : hrjin@kyobobook.com
     * @description : 
     * @param device
     * @return
     */
    @GetMapping("/")
    public String main(Device device) {
        LOGGER.info("* device view: " + device.getDevice());
        return CommonUtils.viewPathResolve("/index", device.getDevice());
    }
    
    @GetMapping("/detail")
    public String detail(Device device) {
        LOGGER.info("* detail view: " + device.getDevice());
        return CommonUtils.viewPathResolve("/detail/index", device.getDevice());
    }
    
    @GetMapping("/ink")
    public String mainInk() {
        LOGGER.info("* ink view");
        return CommonUtils.viewPathResolve("/ink/");
    }
    
    @GetMapping("/mok")
    public String mainMok() {
        LOGGER.info("* mok view");
        return CommonUtils.viewPathResolve("/mok/");
    }
    
    /**
     * TODO: remove sample code
     * @Method      : sample
     * @Date        : 2021. 11. 17.
     * @author      : eykim@kyobobook.com
     * @description : 
     * @param model
     * @return
     */
    @GetMapping("/sample/welcome")
    public String sample(Model model) {
        List<String> tasks = Arrays.asList("a", "b", "c", "d", "e", "f", "g");
        
        // inject via application.properties
        model.addAttribute("message", env.getProperty("resource.dummy.message"));
        model.addAttribute("tasks", tasks);
    
        return CommonUtils.viewPathResolve("/sample/welcome");
    }
    
    /**
     * TODO: remove sample code
     * @Method      : sampleWithParam
     * @Date        : 2021. 11. 17.
     * @author      : eykim@kyobobook.com
     * @description : /hello?name=kotlin
     * @param name
     * @param model
     * @return
     */
    @GetMapping("/sample/hello")
    public String sampleWithParam(
            @RequestParam(name = "name", required = false, defaultValue = "") 
      String name, Model model) {
    
        model.addAttribute("message", name);
    
        return CommonUtils.viewPathResolve("/sample/welcome");
    }
    
    @GetMapping("/login")
    public String loginMain(Device device) {
        return CommonUtils.viewPathResolve("/login", device.getDevice());
    }
    
    @GetMapping("/onk")
    public String onkMain(Device device) {
        LOGGER.info("onk test...");
        return CommonUtils.viewPathResolve("/onk", device.getDevice());
    }
    
    // TODO :: to remove
    @GetMapping("/test")
    public String errorTest() {     
        // 500 error를 얻기 위한 없는 페이지 호출
        return CommonUtils.viewPathResolve("/sample/welcome2");
    }
}
