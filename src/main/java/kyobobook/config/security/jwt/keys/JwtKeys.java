/***************************************************
 * Copyright(c) 2021-2022 Kyobo Book Centre All right reserved.
 * This software is the proprietary information of Kyobo Book.
 *
 * Revision History
 * Author                         Date          Description
 * --------------------------     ----------    ----------------------------------------
 * smlee1@kyobobook.com           2021. 9. 24.  First Draft.
 *
 ****************************************************/
package kyobobook.config.security.jwt.keys;

import java.security.PrivateKey;
import java.security.PublicKey;

import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Builder;
import lombok.Data;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtKeys.java
 * @Date        : 2021. 9. 24.
 * @author      : smlee1@kyobobook.com
 * @description : jwt token 생성 키 관리
 */
@Data
@Builder
public class JwtKeys {

    private PrivateKey privateKey;
    private PublicKey publicKey;
    private SignatureAlgorithm algorithm; 
}
