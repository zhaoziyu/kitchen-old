package com.restaurant.dinner.market.common.security;

import org.javatuples.Pair;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

/**
 * Kitchen框架自定义加密
 * 融合MD5和PBKDF2算法
 *
 * @date 2017-01-02
 * @author 赵梓彧 - kitchen_dev@163.com
 */
public class KitEncryptionCustom {

    /**
     * 返回参数说明：
     * 1、盐（原文）
     * 2、密文
     * @param content
     * @return
     */
    public static Pair<String, String> encrypt(String content) {
        String salt = "";
        String ciphertext = "";
        try {
            Pair<String, String> result = KitEncryptionPBKDF2.encryptByRandomSalt(content);
            salt = result.getValue0();

            ciphertext = KitEncryptionMD5.encrypt(result.getValue0() + result.getValue1());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return Pair.with(salt, ciphertext);
    }

    public static String encrypt(String content, String salt) {
        String ciphertext = "";

        try {
            Pair<String, String> result = KitEncryptionPBKDF2.encryptByFixSalt(content, salt);

            ciphertext = KitEncryptionMD5.encrypt(result.getValue0() + result.getValue1());
        } catch (InvalidKeySpecException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return ciphertext;
    }

}
