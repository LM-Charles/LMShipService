package com.longmendelivery.lib.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by  rabiddesireon 17/06/15.
 */
public class SecurityUtil {
    static SecureRandom secureRandom;

    static {
        secureRandom = new SecureRandom();
    }

    public static String generateSecureVerificationCode() {
        return new BigInteger(70, secureRandom).toString(32);
    }

    public static String md5(String password) {
        try {
            byte[] bytes = password.getBytes("UTF-8");
            byte[] digest = MessageDigest.getInstance("MD5").digest(bytes);
            return new String(digest, "UTF-8");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static String generateSecureToken() {
        return new BigInteger(240, secureRandom).toString(32);

    }
}
