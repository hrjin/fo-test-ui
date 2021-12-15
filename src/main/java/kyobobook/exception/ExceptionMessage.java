/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 8. 24.
 *
 ****************************************************/
package kyobobook.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : ExceptionMessage.java
 * @Date        : 2021. 8. 24.
 * @author      : smlee1@kyobobook.com
 * @description : Exception Handler 에서 Client 전달하는 Message class
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExceptionMessage {
    private String resultCode;
    private String resultMessage;
    private int detailCode;
    private String detailMessage;
}
