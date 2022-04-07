package com.gmail.vanyasudnishnikov.authorization.service;

public interface AttemptService {
    Boolean addByUsername(String username);

    Boolean removeByUsername(String username);

    Integer get(String username);
}
