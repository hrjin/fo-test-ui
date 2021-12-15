/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 29.
 *
 ****************************************************/
package kyobobook.common;

/**
 * @Project     : bo-prototype-ui
 * @FileName    : ErrorCode.java
 * @Date        : 2021. 9. 29.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
public enum ErrorCode {

    MalformedJwtException(1)
    , ExpiredJwtException(2)
    , UnsupportedJwtException(3)
    , IllegalArgumentException(4)
    , NoSuchAlgorithmException(5)
    , InvalidKeySpecException(6);
    
    private final int code;
    
    ErrorCode(int code) {
        this.code = code;
    }
    
    public int getCode() {
        return code;
    }
}
