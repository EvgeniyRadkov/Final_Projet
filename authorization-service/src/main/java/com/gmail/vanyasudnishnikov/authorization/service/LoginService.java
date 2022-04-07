package com.gmail.vanyasudnishnikov.authorization.service;

public interface LoginService {
    Boolean isUsername(String login);

    Boolean isUserMail(String login);
}
