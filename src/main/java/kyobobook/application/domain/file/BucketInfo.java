/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 8. 18.
 *
 ****************************************************/
package kyobobook.application.domain.file;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : SampleBucket.java
 * @Date        : 2021. 8. 18.
 * @author      : smlee1@kyobobook.com
 * @description : Bucket 정보
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BucketInfo {
    private Date creationDate;
    private String name;
    private String displayName;
    private String id;
}
