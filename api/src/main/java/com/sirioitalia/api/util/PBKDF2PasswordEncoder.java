package com.sirioitalia.api.util;

import com.sirioitalia.api.exception.CannotPerformOperationException;
import com.sirioitalia.api.exception.InvalidHashException;
import org.springframework.security.crypto.password.PasswordEncoder;


public class PBKDF2PasswordEncoder implements PasswordEncoder {

    public PBKDF2PasswordEncoder() {

    }

    public String encode(CharSequence cs) {
        try {
            return PasswordHash.createHash(cs.toString());
        } catch (CannotPerformOperationException ex) {
            throw new RuntimeException(ex);
        }
    }

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