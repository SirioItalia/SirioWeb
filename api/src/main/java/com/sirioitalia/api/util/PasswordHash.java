package com.sirioitalia.api.util;

import com.sirioitalia.api.configuration.SecurityConfiguration;
import com.sirioitalia.api.exception.CannotPerformOperationException;
import com.sirioitalia.api.exception.InvalidHashException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.HashMap;

public class PasswordHash {
    private static final String PBKDF2_ALGORITHM = "PBKDF2WithHmacSHA1";

    private static final int KEY_LENGTH = 32;
    private static final int SALT_BYTE_SIZE = 64;
    private static final int HASH_BYTE_SIZE = 64;


    private static final int HASH_SECTIONS = 2;
    private static final int SALT_INDEX = 1;
    private static final int HASH_INDEX = 0;

    private static final int PBKDF2_ITERATIONS = 10000;

    private static final String PEPPER = "jD7umHzn5vPGpdwBBpGrkAtzsa7uP3fYxc4HwuUczkJN9QZAPxRZHUqYU75kYVU7YAE6Tb84mtt7UhxzEdacJaNu7HrHeVEPmVLQWvKTz3QjbYHFNJvpv8ttsHZgvvWs2y8NDHj3AKWBzgTdBdcVWvdYPP69VSrM2pcwGm98GWz7PhVf7X2ThRuWgrG3TJsLHeSfrRYHq9E4hb9ZKMwEePcmQKZA53bp7Te8m4FB4WvWXfMzDT4Hz6EZsfr78cm5";

    public static String createHash(String password)
            throws CannotPerformOperationException {
        return createHash(password.toCharArray());
    }

    public static String createHash(char[] password)
            throws CannotPerformOperationException {

        SecurityConfiguration securityConfiguration;
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_BYTE_SIZE];
        random.nextBytes(salt);

        // Hash the password
        byte[] hash = pbkdf2(password, salt);
        int hashSize = hash.length;

        String parts = toBase64(hash) +
                ":" +
                toBase64(salt);
        return parts;
    }

    public static boolean verifyPassword(String password, String correctHash)
            throws CannotPerformOperationException, InvalidHashException {
        return verifyPassword(password.toCharArray(), correctHash);
    }

    public static boolean verifyPassword(char[] password, String correctHash)
            throws CannotPerformOperationException, InvalidHashException {
        // Decode the hash into its parameters
        String[] params = correctHash.split(":");
        if (params.length != HASH_SECTIONS) {
            throw new InvalidHashException(
                    "Fields are missing from the password hash."
            );
        }

        byte[] salt = null;
        try {
            salt = fromBase64(params[SALT_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                    "Base64 decoding of salt failed.",
                    ex
            );
        }

        byte[] hash = null;
        try {
            hash = fromBase64(params[HASH_INDEX]);
        } catch (IllegalArgumentException ex) {
            throw new InvalidHashException(
                    "Base64 decoding of pbkdf2 output failed.",
                    ex
            );
        }

        byte[] pepper = PEPPER.getBytes(StandardCharsets.UTF_8);
        ByteArrayOutputStream saltAndPepper = new ByteArrayOutputStream();

        try {
            saltAndPepper.write(salt);
            saltAndPepper.write(pepper);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

        byte[] testHash = pbkdf2(password, saltAndPepper.toByteArray());
        // Compare the hashes in constant time. The password is correct if
        // both hashes match.
        return slowEquals(hash, testHash);
    }

    private static boolean slowEquals(byte[] a, byte[] b) {
        int diff = a.length ^ b.length;
        for (int i = 0; i < a.length && i < b.length; i++)
            diff |= a[i] ^ b[i];
        return diff == 0;
    }

    private static byte[] pbkdf2(char[] password, byte[] salt)
            throws CannotPerformOperationException {
        try {
            byte[] pepper = PEPPER.getBytes(StandardCharsets.UTF_8);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            outputStream.write(salt);
            outputStream.write(pepper);

            byte[] saltAndPepper = outputStream.toByteArray();

            PBEKeySpec spec = new PBEKeySpec(password, saltAndPepper, PBKDF2_ITERATIONS, HASH_BYTE_SIZE * 8);
            SecretKeyFactory skf = SecretKeyFactory.getInstance(PBKDF2_ALGORITHM);
            return skf.generateSecret(spec).getEncoded();
        } catch (NoSuchAlgorithmException ex) {
            throw new CannotPerformOperationException(
                    "Hash algorithm not supported.",
                    ex
            );
        } catch (InvalidKeySpecException ex) {
            throw new CannotPerformOperationException(
                    "Invalid key spec.",
                    ex
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static byte[] fromBase64(String hex)
            throws IllegalArgumentException {
        return Base64.getDecoder().decode(hex);
    }

    private static String toBase64(byte[] array) {
        return Base64.getEncoder().encodeToString(array);
    }

}