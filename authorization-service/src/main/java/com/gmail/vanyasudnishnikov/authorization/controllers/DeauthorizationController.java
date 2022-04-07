package com.gmail.vanyasudnishnikov.authorization.controllers;

import com.gmail.vanyasudnishnikov.authorization.service.JwtTokenService;
import com.gmail.vanyasudnishnikov.authorization.service.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth/logout")
public class DeauthorizationController {
    private static final String SESSION_IS_CLOSED = "Session is closed.";
    private static final String IS_NOT_CLOSED = "The session is not closed.";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String BEARER_TOKEN = "Bearer ";
    private JwtTokenService jwtTokenService;
    private HttpServletRequest httpServletRequest;
    private JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<String> logoutEmployee(@RequestParam String username) {
        String headerAuth = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if (headerAuth != null) {
            String jwt = headerAuth.substring(BEARER_TOKEN.length());
            boolean validateJwtToken = jwtUtil.validateJwtToken(jwt);
            if (validateJwtToken) {
                jwtTokenService.close(jwt);
            }
            Boolean closeByUsername = jwtTokenService.closeByUsername(username);
            if (closeByUsername) {
                return ResponseEntity.ok()
                        .body(SESSION_IS_CLOSED);
            }
        }
        return ResponseEntity.badRequest()
                .body(IS_NOT_CLOSED);
    }
}

