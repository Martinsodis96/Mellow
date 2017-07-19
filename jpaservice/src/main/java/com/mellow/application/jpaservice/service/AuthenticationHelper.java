package com.mellow.application.jpaservice.service;

import com.mellow.application.jpaservice.service.exception.HashingException;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

public final class AuthenticationHelper {

    public static final int hashingIterations = 100000;
    private static final int hashSize = 2048;

    public static String generateSalt() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] salt = new byte[hashSize / 8];
        secureRandom.nextBytes(salt);
        return new String(salt);
    }

    public static String hashPassword(final String password, final String salt) throws HashingException {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            PBEKeySpec spec = new PBEKeySpec(password.toCharArray(), salt.getBytes(), hashingIterations, hashSize);
            SecretKey key = skf.generateSecret(spec);
            return new String(key.getEncoded());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new HashingException(String.format("Cannot hash '%s'", password), e);
        }
    }
}