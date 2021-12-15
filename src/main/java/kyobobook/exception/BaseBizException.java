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

/**
 * @Project     : fo-ui-proto-r2
 * @FileName    : BaseBizException.java
 * @Date        : 2021. 8. 24.
 * @author      : smlee1@kyobobook.com
 * @description : Custom Exception 의 기본 클래스
 */
public class BaseBizException extends RuntimeException {
    private static final long serialVersionUID = 5930650721134016714L;
    
    private int errorCode;
    private String errorMessage;
    
    public BaseBizException(String errorMessage) {
        super(errorMessage);
        this.errorMessage = errorMessage;
    }

    public BaseBizException(int errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    };

    public BaseBizException(int errorCode, String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    };

    public BaseBizException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
        this.errorMessage = errorMessage;
    }

    public BaseBizException(Throwable cause) {
        super(cause);
    }

    public int getErrorCode(){
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }
}
