package com.sirioitalia.api.configuration;

import com.sirioitalia.api.util.PBKDF2PasswordEncoder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

@ConfigurationProperties("security")
public record SecurityConfiguration(String pepper, Integer iterations, Integer keyLen) {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new PBKDF2PasswordEncoder();
    }
}
