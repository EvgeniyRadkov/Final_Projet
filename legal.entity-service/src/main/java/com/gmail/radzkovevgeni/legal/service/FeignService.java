package com.gmail.radzkovevgeni.legal.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "post", url = "http://localhost:8080/api/auth")
public interface FeignService {

    @GetMapping
    Boolean isValid(@RequestParam("token") String token);
}
