package com.example.realmadridmanagersystem290322.utils;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CreatePasswordEncode {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

        String password_1 = passwordEncoder.encode("password_one");
        String password_2 = passwordEncoder.encode("password_two");

        System.out.println(password_1);
        System.out.println(password_2);
    }
}
