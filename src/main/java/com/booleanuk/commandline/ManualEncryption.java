package com.booleanuk.commandline;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class ManualEncryption {
    public static void main(String[] args) {
        String password = "password";
        PasswordEncoder encoder = new BCryptPasswordEncoder();
        String encoded = encoder.encode(password);
        System.out.println(encoded);
    }
}