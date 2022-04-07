package com.gmail.vanyasudnishnikov.application.service.util;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@Configuration
public class JwtUtil {
    private static final String JWT_SECRET = "my-secret";
    private static final int JWT_EXPIRATION_MS = 800000;

    public String getUsernameFromJwtToken(String token) {

        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
