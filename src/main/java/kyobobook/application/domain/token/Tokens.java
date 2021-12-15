/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * hrjin@kyobobook.com      2021. 11. 29.
 *
 ****************************************************/
package kyobobook.application.domain.token;

import lombok.Data;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : Token.java
 * @Date        : 2021. 11. 29.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Data
public class Tokens {
    public String accessToken;
    public String refreshToken;
}
