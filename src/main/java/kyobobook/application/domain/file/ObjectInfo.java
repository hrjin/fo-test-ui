/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 8. 19.
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
 * @FileName    : ObjectInfo.java
 * @Date        : 2021. 8. 19.
 * @author      : smlee1@kyobobook.com
 * @description : Bucket 의 파일 정보
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObjectInfo {

    private String bucketName;
    private String eTag;
    private String key;
    private Date lastModified;
    private long size;
    private String displayName;
    private String id;
}
