/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 24.
 *
 ****************************************************/
package kyobobook.application.biz.api.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import kyobobook.application.biz.api.port.in.OnkPort;
import kyobobook.application.domain.main.EmployeeList;
import kyobobook.exception.BizRuntimeException;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : OnkService.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Service
@RequiredArgsConstructor
public class OnkService implements OnkPort {
    
    @Value("${resource.dummy.restApiHost}")
    private String foApiUrl;
    
    private static final Logger logger = LoggerFactory.getLogger(OnkService.class);
    
    private final WebClient webClient;
    
    @Override
    public EmployeeList getMembers() {
        return webClient.mutate()
                        .build()
                        .get()
                        .uri(foApiUrl + "/employees")
                        .retrieve()
                        .onStatus(status -> status.is4xxClientError() 
                                || status.is5xxServerError()
                                , clientResponse -> Mono.error(new BizRuntimeException(clientResponse.statusCode().toString())))
                        .bodyToMono(EmployeeList.class)
                        .block();
    }
}
