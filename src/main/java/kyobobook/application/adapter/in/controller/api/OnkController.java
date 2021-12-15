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
package kyobobook.application.adapter.in.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import kyobobook.application.biz.api.port.in.OnkPort;
import kyobobook.application.domain.main.EmployeeList;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : OnkController.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@RestController
@RequestMapping("/api/main")
public class OnkController {
    private static final Logger logger = LoggerFactory.getLogger(OnkController.class);
    
    private OnkPort onkService;

    public OnkController(OnkPort onkService) {
        this.onkService = onkService;
    }

    @RequestMapping("/members")
    private EmployeeList getMembers() {
        logger.info("members :: " + onkService.getMembers().toString());
        
        return onkService.getMembers();
    }
}
