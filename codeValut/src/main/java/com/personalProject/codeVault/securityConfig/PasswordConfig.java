package com.personalProject.codeVault.securityConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
public class PasswordEncoder {

    @Bean //means that spring will automatically create a object and manage it.
    public org.springframework.security.crypto.password.PasswordEncoder passwordEncoderService(){
        return new BCryptPasswordEncoder();
    }

    public void matches(String inputPassword, String password) {
    }
}
