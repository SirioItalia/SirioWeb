package com.sirioitalia.api.util;

import com.sirioitalia.api.exception.CannotPerformOperationException;
import com.sirioitalia.api.exception.InvalidHashException;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.security.crypto.password.PasswordEncoder;



public class PBKDF2PasswordEncoder implements PasswordEncoder {

    @Override
    public String encode(CharSequence cs) {
        try {
            return PasswordHash.createHash(DigestUtils.sha256Hex(cs.toString()));
        } catch (CannotPerformOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

    @Override
    public boolean matches(CharSequence cs, String string) {
        try {
            return PasswordHash.verifyPassword(cs.toString(), string);
        } catch (InvalidHashException ex) {
            throw new RuntimeException(ex);
        } catch (CannotPerformOperationException ex) {
            throw new RuntimeException(ex);
        }
    }
}