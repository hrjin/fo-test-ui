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
package kyobobook.application.domain.main;

import java.util.List;
import lombok.Data;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : EmployeeList.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Data
public class EmployeeList {
    private String status;
    private List<Employee> data;
    private String message;

    private int totalCnt;
}
