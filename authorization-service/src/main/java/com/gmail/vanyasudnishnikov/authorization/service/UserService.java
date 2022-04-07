package com.gmail.vanyasudnishnikov.authorization.service;

import com.gmail.vanyasudnishnikov.authorization.service.model.LoginDTO;

public interface UserService {
    Boolean isValid(LoginDTO loginDTO);
}
