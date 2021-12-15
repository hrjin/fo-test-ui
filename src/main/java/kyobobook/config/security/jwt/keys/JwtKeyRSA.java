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

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import org.springframework.stereotype.Component;

/**
 * @Project     : fo-prototype-ui
 * @FileName    : JwtRsaKey.java
 * @Date        : 2021. 9. 24.
 * @author      : smlee1@kyobobook.com
 * @description : jwt 에서 사용하는 RSA 형식의 키 발행
 */
@Component("jwtKeyRSA")
public class JwtKeyRSA implements JwtKeyManager {

    /**
     * @Method      : getPublicKeyFromBase64String
     * @Date        : 2021. 10. 12.
     * @author      : smlee1@kyobobook.com
     * @description : RSA 알고리즘 public key 생성
     * @param key - 공개 키
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    private PublicKey getPublicKeyFromBase64String(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        final String publicKeyString = key.replaceAll("\\n", "").replaceAll("-{5}[ a-zA-Z]*-{5}", ""); 
        KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
        X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64.getDecoder().decode(publicKeyString)); 
        return keyFactory.generatePublic(keySpecX509);
    }
    
    @Override
    public JwtKeys getKeys(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
        return JwtKeys.builder()
                .publicKey(this.getPublicKeyFromBase64String(key))
                .build();
    }
}
