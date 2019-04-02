package com.lion.core.helper;

import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by JACK on 2015/9/9.
 */
public class EncryptionHelper {
    public static final String KEY_MD5 = "MD5";

    /**
     * BASE64解密
     *
     * @param key
     * @return
     * @throws IOException
     * @throws Exception
     */
    public static byte[] decryptBASE64(String key) throws IOException {
        return (new BASE64Decoder()).decodeBuffer(key);
    }

    /**
     * BASE64加密
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptBASE64(byte[] key) {
        return (new BASE64Encoder()).encodeBuffer(key);
    }

    /**
     * MD5加密
     *
     * @param data
     * @return
     * @throws NoSuchAlgorithmException
     * @throws Exception
     */
    public static String encryptMD5(byte[] data) {

        MessageDigest md5;
        String result = null;
        try {
            md5 = MessageDigest.getInstance(KEY_MD5);
            md5.update(data);
            BASE64Encoder encoder = new BASE64Encoder();
            result = encoder.encode(md5.digest());

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String signDataWithToken(String signData, String token) {
        if(StringUtils.isEmpty(signData) || StringUtils.isEmpty(token)) return null;
        String signedData = null;
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret = new SecretKeySpec(token.getBytes("UTF-8"), mac.getAlgorithm());
            mac.init(secret);
            byte[] digest = mac.doFinal(signData.getBytes("UTF-8"));
            signedData = EncryptionHelper.encryptBASE64(digest).trim();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return signedData;
    }
}
