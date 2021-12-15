/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 9. 6.   First Draft.
 *
 ****************************************************/
package kyobobook.exception;

/**
 * @Project     : common-prototype-sub-api
 * @FileName    : BizRuntimeException.java
 * @Date        : 2021. 9. 6.
 * @author      : smlee1@kyobobook.com
 * @description : Custom Runtime Exception
 */
public class BizRuntimeException extends BaseBizException {

    private static final long serialVersionUID = 4403082901256044060L;

    /**
     * Constructor
     * @param errorMessage - 에러 메세지
     */
    public BizRuntimeException(String errorMessage) {
        super(errorMessage);
    }
    
    /**
     * Constructor
     * @param cause - Throwable
     */
    public BizRuntimeException(Throwable cause) {
        super(cause);
    } 
    /**
     * Constructor
     * @param errorCode - 에러 코드
     * @param errorMessage - 에러 메세지
     */
    public BizRuntimeException(int errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
    
    /**
     * @param errorMessage - 에러 메세지
     * @param cause - Throwable
     */
    public BizRuntimeException(String errorMessage, Throwable cause) {
        super(errorMessage, cause);
    }
    
    /**
     * Constructor
     * @param errorCode - 에러 코드
     * @param errorMessage - 에러 메세지
     * @param cause - Throwable
     */
    public BizRuntimeException(int errorCode, String errorMessage, Throwable cause) {
        super(errorCode, errorMessage, cause);
    }
}
