package com.gmail.vanyasudnishnikov.employee.repository.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "get", url = "http://localhost:8080/api/auth")
public interface TokenFeign {

    @GetMapping
    Boolean isValid(@RequestParam("token") String token);
}
