package com.gmail.vanyasudnishnikov.authorization.controllers;

import com.gmail.vanyasudnishnikov.authorization.service.JwtTokenService;
import com.gmail.vanyasudnishnikov.authorization.service.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth")
public class TokenValidationController {
    private JwtUtil jwtUtil;
    private JwtTokenService jwtTokenService;


    @GetMapping
    @PostAuthorize("hasRole('ADMIN')")
    public Boolean isValid(@RequestParam(name = "token") String token) {
        return !jwtTokenService.isClosed(token);
    }
}
