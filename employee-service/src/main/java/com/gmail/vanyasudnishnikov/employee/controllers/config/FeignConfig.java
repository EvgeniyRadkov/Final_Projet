package com.gmail.vanyasudnishnikov.employee.controllers.config;

import feign.RequestInterceptor;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableFeignClients(basePackages = "com.gmail.vanyasudnishnikov.employee.repository.feign")
public class FeignConfig {

    private static final String ADMIN_TOKEN = "Bearer eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbiIsImlhdCI6MTY0NzM0NzI1MiwiZXhwIjo0MzA0MzM0NjAwMH0.-yCdWuX-7tgzWcRyDt2K5uj5eGB6OT9OOzLTyHO24HcRiGvEb5dJT6CkroKxOzcBfWcvdaNFXXXto-7c9YZaKw";
    private static final String AUTHORIZATION_HEADER = "Authorization";

    @Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> requestTemplate.header(AUTHORIZATION_HEADER, ADMIN_TOKEN);
    }
}
