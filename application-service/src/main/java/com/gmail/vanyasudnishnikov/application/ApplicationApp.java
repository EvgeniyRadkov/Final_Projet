package com.gmail.vanyasudnishnikov.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class ApplicationApp {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationApp.class, args);
    }
}
