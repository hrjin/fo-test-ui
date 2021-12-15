/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 16.
 *
 ****************************************************/
package kyobobook.config.web;

import java.util.List;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import kyobobook.common.DeviceHandler;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : WebMvcConfigure.java
 * @Date        : 2021. 11. 16.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Configuration
public class WebMvcConfigure implements WebMvcConfigurer {

    /**
     * HandlerResolver 등록
     * 
     */
    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        resolvers.add(deviceHandler());
    }
    
    @Bean
    public DeviceHandler deviceHandler() { 
        return new DeviceHandler(); 
    }
}
