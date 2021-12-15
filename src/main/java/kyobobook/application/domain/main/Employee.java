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

import lombok.Data;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : Employee.java
 * @Date        : 2021. 11. 24.
 * @author      : hrjin@kyobobook.com
 * @description :
 */
@Data
public class Employee {
    private String id;
    private String employee_name;
    private int employee_salary;
    private int employee_age;
}
