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
package kyobobook.application.adapter.out.file;

import kyobobook.exception.BaseBizException;

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : AwsS3ProcessException.java
 * @Date        : 2021. 8. 24.
 * @author      : smlee1@kyobobook.com
 * @description : AwsS3 처리 중 발생되는 Exception 처리
 */
public class AwsS3RuntimeException extends BaseBizException {

    private static final long serialVersionUID = -349841834220333611L;

    /**
     * Constructor
     * @param errorMessage - 에러 메세지
     */
    public AwsS3RuntimeException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Constructor
     * @param cause - Throwable
     */
    public AwsS3RuntimeException(Throwable cause) {
        super(cause);
    } 
    /**
     * Constructor
     * @param errorCode - 에러 코드
     * @param errorMessage - 에러 메세지
     */
    public AwsS3RuntimeException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    /**
     * @param errorMessage - 에러 메세지
     * @param cause - Throwable
     */
    public AwsS3RuntimeException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
    
    /**
     * Constructor
     * @param errorCode - 에러 코드
     * @param errorMessage - 에러 메세지
     * @param cause - Throwable
     */
    public AwsS3RuntimeException(int errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }

}
