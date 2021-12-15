/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 8. 12.  First Draft.
 *
 ****************************************************/
package kyobobook.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import kyobobook.application.adapter.out.file.AwsS3RuntimeException;

/**
 * @Project     : common-prototype-api
 * @FileName    : ControllerExceptionHandler.java
 * @Date        : 2021. 8. 12.
 * @author      : smlee1@kyobobook.com
 * @description : Controller 에서 throw 한 Exception 의 공통 처리
 */
@RestControllerAdvice
public class ControllerExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(ControllerExceptionHandler.class);
 
    /**
     * @Method      : handleException
     * @Date        : 2021. 8. 24.
     * @author      : smlee1@kyobobook.com
     * @description : AWS S3 파일 처리중 발생 오류 Handler
     * @param e
     * @return
     */
    @ExceptionHandler({AwsS3RuntimeException.class})
    @ResponseBody
    public ResponseEntity<?> handleException(AwsS3RuntimeException e) {
        return new ResponseEntity<ExceptionMessage>(ExceptionMessage.builder()
                .resultMessage(e.getMessage())
                .detailMessage(e.getCause().getMessage())
                .detailCode(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * @Method      : handleException
     * @Date        : 2021. 9. 10.
     * @author      : smlee1@kyobobook.com
     * @description : Service 로직 처리 중 발생 오류의 공통 Handler 
     * @param e
     * @return
     */
    @ExceptionHandler({BizRuntimeException.class})
    @ResponseBody
    public ResponseEntity<?> handleException(BizRuntimeException e) {
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * @Method      : handlException
     * @Date        : 2021. 8. 12.
     * @author      : smlee1@kyobobook.com
     * @description : 모든 Exception을 핸들링
     * @param e
     */
    @ExceptionHandler({Exception.class})
    public void handlException(Exception e) {
        logger.error("Exception : #####################################");
        logger.error("Exception : ", e);
    }
}
