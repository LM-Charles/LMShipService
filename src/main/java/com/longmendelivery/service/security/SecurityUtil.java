package com.longmendelivery.service.security;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * Created by desmond on 17/06/15.
 */
public class SecurityUtil {
    public static final String UTF_8 = "UTF-8";
    public static final String MD_5 = "MD5";

    static SecureRandom secureRandom;

    static {
        secureRandom = new SecureRandom();
    }

    public static String generateSecureVerificationCode() {
        return new BigInteger(70, secureRandom).toString(32);
    }

    public static String md5(String password) {
        try {
            byte[] bytes = password.getBytes(UTF_8);
            byte[] digest = MessageDigest.getInstance(MD_5).digest(bytes);
            return new String(digest, UTF_8);
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
