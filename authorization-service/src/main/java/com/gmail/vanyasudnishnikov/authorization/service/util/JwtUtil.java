package com.gmail.vanyasudnishnikov.authorization.service.util;


import com.gmail.vanyasudnishnikov.authorization.repository.model.JwtToken;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
@Configuration
public class JwtUtil {

    private static final String JWT_SECRET = "my-secret";
    private static final int JWT_EXPIRATION_MS = 800000;

    public JwtToken generateJwtToken(String username) {
        JwtToken jwtToken = new JwtToken();
        Date dateStart = new Date();
        jwtToken.setSessionStart(dateStart);
        Date dateEnd = new Date((new Date()).getTime() + JWT_EXPIRATION_MS);
        jwtToken.setSessionEnd(dateEnd);
        String token = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(dateStart)
                .setExpiration(new Date((new Date()).getTime() + JWT_EXPIRATION_MS))
                .signWith(SignatureAlgorithm.HS512, JWT_SECRET)
                .compact();
        jwtToken.setJwtToken(token);
        return jwtToken;
    }

    public String getUsernameFromJwtToken(String token) {

        return Jwts.parser()
                .setSigningKey(JWT_SECRET)
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(JWT_SECRET).parseClaimsJws(authToken);
        } catch (ExpiredJwtException e) {
            log.error("JWT token is expired: {}", e.getMessage());
            return false;
        } catch (UnsupportedJwtException e) {
            log.error("JWT token is unsupported: {}", e.getMessage());
            return false;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token: {}", e.getMessage());
            return false;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature: {}", e.getMessage());
            return false;
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty: {}", e.getMessage());
            return false;
        }
        return true;
    }
}
