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
package kyobobook.common;

import javax.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import kyobobook.application.domain.device.Device;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : DeviceHandler.java
 * @Date        : 2021. 11. 16.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
public class DeviceHandler implements HandlerMethodArgumentResolver {

    /**
     * 특정 클래스 타입의 객체 파라미터 정의
     * 
     */
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(Device.class);
    }

    
    /**
     * controller로 전달할 객체에 대한 조작을 한 뒤 해당 객체를 리턴
     * 
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
            NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        
        String device = "ink";
        boolean isMobile = CommonUtils.isMobile(request);
        
        if(isMobile) {
            device = "mok";
        }
        
        return new Device(device);
    }
    
}
