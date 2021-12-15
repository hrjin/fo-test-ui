/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 11.
 *
 ****************************************************/
package kyobobook.application.adapter.in.controller.api;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.map.HashedMap;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import nl.basjes.parse.useragent.UserAgent;
import nl.basjes.parse.useragent.UserAgentAnalyzer;

/**
 * @Project     : fo-demo-ui
 * @FileName    : UserAgentTestController.java
 * @Date        : 2021. 11. 11.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Controller
public class UserAgentTestController {
    
    @GetMapping("/userAgent")
    public String getUserAgentInfo(HttpServletRequest request, Model model) {
        UserAgentAnalyzer uaa = UserAgentAnalyzer
                .newBuilder()
                .hideMatcherLoadStats()
                .withCache(10000)
                .build();
        
        UserAgent agent = uaa.parse(request.getHeader("User-Agent"));
        

        List<Map<String, String>> agentInfos = new ArrayList<>();
        Map<String, String> agentInfo = new HashedMap<>();
        
        for (String fieldName: agent.getAvailableFieldNamesSorted()) {
            agentInfo.put(fieldName, agent.getValue(fieldName));
        }
        
        agentInfos.add(agentInfo);
   
        model.addAttribute("agentInfoList", agentInfos);
        
        return "agentTest";
    }
}
