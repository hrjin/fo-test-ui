/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com      2021. 9. 24.
 *
 ****************************************************/
package kyobobook.config.security.jwt.keys;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtKeyManager.java
 * @Date        : 2021. 9. 24.
 * @author      : smlee1@kyobobook.com
 * @description :
 */
public interface JwtKeyManager {

    /**
     * @Method      : getKeys
     * @Date        : 2021. 9. 24.
     * @author      : smlee1@kyobobook.com
     * @description : auth 서버를 통해 받은 공개키로 토큰 서명에 필요한 공개키(PublicKey) 파일 생성
     * @return
     * @throws Exception
     */
    JwtKeys getKeys(String key) throws NoSuchAlgorithmException, InvalidKeySpecException ;
}
