package com.gmail.vanyasudnishnikov.authorization.service;

public interface JwtTokenService {
    String createByUsername(String username) throws IllegalArgumentException;

    Boolean close(String jwtToken);

    Boolean closeByUsername(String username);

    Boolean isClosed(String jwtToken);
}
