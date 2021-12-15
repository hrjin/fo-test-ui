/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 30.
 *
 ****************************************************/
package kyobobook.application.biz.api.port.in;

import kyobobook.application.domain.token.Tokens;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : VerifyPort.java
 * @Date        : 2021. 11. 29.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
public interface AuthPort {
    <T> T getAccessToken(Tokens tokens);
}
