/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 26.
 *
 ****************************************************/
package kyobobook.application.adapter.in.controller.api;

import java.io.IOException;
import java.net.URLEncoder;
import javax.servlet.ServletException;
// import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kyobobook.common.Constants;
import kyobobook.utils.CookieUtil;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : LoginController.java
 * @Date        : 2021. 11. 26.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Controller
@RequestMapping("/api/login")
public class LoginController {
    
    private final CookieUtil cookieUtil;
    
    /**
     * @param cookieUtil
     */
    public LoginController(CookieUtil cookieUtil) {
        this.cookieUtil = cookieUtil;
    }

    @GetMapping
    public String login(HttpServletResponse response) throws IOException, ServletException {
        // 유효기간 12/11 새벽 2시 57분
        String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTYzODE1OTEyMiwiZXhwIjoxNjM5MTU5MDIyLCJyb2xlcyI6WyJVU0VSIl19.A0v9cwH9ojws3F8fgbR4hDqAzg2M6UtV5z0VrOReGuVnsEDoL2O_pYaIuhWQho-mW7R9EHGIXbP-tWRwP2Y5mxU0Qbh9bYOuvuBPhBOBd_7D8h2KtuwO-aVuc9zFucY2yTr75vHkxQZaTW_a7PUiakSVYWyw_Dh1y3gy1ohj6tbgVMEwlXlCMrE1RhcAS9Lg_jpaJZx1BDJU-DUMYo1uGTGXWRRJsb3EGbmOmgiDuA46brgMOMWOnkO963Id1xnhfmxZ3dyxmk46TkAP9n2zNQ_uZBrAwpbFkLs0ZVvD0lTSzmm0nG9FXH3PdPnY0T9X3vAgCPYh2oQ8Y-BE1oFeaVUSCdPNc1NAFF5Xn9g4LmqHlocKvRSRC3aaNeeJtC4AhDDfo7EpN1Om_MvXUyWyf1PXmm39ADj_iMR7QoG0jFio73HedKcuSJl4TMryH0ssvwnThvLnkFDCqphCenWmLCPbuAQUj_AoU5FpX6Kr91gWtLawPU8aqJaT3JQ4H2NYQEudJNMVIIfJIOpGgWVANTcQGnME-yV5t6U8zmkL7iV5uphbty4ZMJqx_VpFLV1NO_LfMlGEWBT4p0eb62S7p7twBTxjSlsxEASXodUf3miozc2poiFahJaBBG6c5fp0AtG-QglvDgQ5ymvYPXG4vgJo7lUQefRSXQm0FkE_Lck";
        
        // 유효기간 지난 토큰 테스트용
        //String tokenValue = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiYWRtaW4iOnRydWUsImlhdCI6MTYzODE1OTEyMiwiZXhwIjoxNjM4MTYwMDIwLCJyb2xlcyI6WyJVU0VSIl19.1ClBexyFI5ZS3YK82GnMi3KyMxZjQRUxrAcSnomPmWvxLKhtDU98mbid6yLu27I7sBX8HQ9LGYSqusPiM9TFRoMAY9luyTUBp6DMP2215Ga55ebSgQx6Rg_JS10_ddT1h_o8A5CoZ8yMQoZD6WNXril27k334MVN6pRNzIkGlD-SYMMDjwhdMwLGutGY3_blaRxt2pqXwNleT71VQzIYAonCTz2y07aKII_JsHG9Kw4y9rPdv3eNKN1G3yOolZwzyDP3OBIVSo-I13d84lNTT-soXAvValzTKV87z0BS5eTky87ZjBxh76SrEWxNnpGDBVkSUJ1mDFdEJCfs7aCTur1GpuKuf6XBu_GXFKr6lsfglGDvETF91HmnrkgutQ9ZWw5v9a112xycBwEvwp2hS0hjgEGGLzvu6SO72GRULiqhPo8nlOKTVZAaOytu6NbluE2ASpasXJiIIYgWm1gyD80CJVFeF60iOM08vIFruyPxEgDI4dYek2gKpdTSlFWHPOeqW4HJo315C_gYRrEwN4HfCy20JzJlcWM_baTi7DkBBQyBnTyoOXHYqyBsA2prSHh7MFVlm8Msm1M9ilNtSPJ1WJ3GPT6cdM_B4hobTF8C-j-O2vX44RuhxJdSAkNUOojriD4cy5W-_sjIN6NQm8LChxoyprVYdg26r1ut5VY";
        
        // 아이디, 패스워드 넘어왔다 치고... 여기서 하드코딩으로 쿠키에 토큰 값 넣어준다....
        // 유효기간은 3600초...
        cookieUtil.setCookie(Constants.COOKIE_ACCESS_TOKEN, URLEncoder.encode(tokenValue.toString(), "UTF-8"), 60 * 60);
        
        return "redirect:/";
    }
}
