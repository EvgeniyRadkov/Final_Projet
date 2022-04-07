package com.gmail.vanyasudnishnikov.authorization.controllers;

import com.gmail.vanyasudnishnikov.authorization.service.*;
import com.gmail.vanyasudnishnikov.authorization.service.model.LoginDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth/login", consumes = MediaType.APPLICATION_JSON_VALUE)
public class AuthorizationController {
    private static final String MESSAGE_WHEN_USER_BLOCKED = "Ваша учетная запись заблокирована. Обратитесь к Администратору";
    private static final String NOT_CORRECT = "Login or password is not correct.";
    private static final int MAX_ATTEMPT = 5;
    private UserService userService;
    private JwtTokenService jwtTokenService;
    private AttemptService attemptService;
    private EmployeeService employeeService;

    @PostMapping
    public ResponseEntity<String> loginEmployee(@RequestBody @Validated LoginDTO loginDTO) {
        Boolean valid = userService.isValid(loginDTO);
        String login = loginDTO.getLogin();
        if (valid) {
            String jwtToken = jwtTokenService.createByUsername(login);
            return ResponseEntity.ok(jwtToken);
        } else {
            Integer attempt = attemptService.get(login);
            if (attempt >= MAX_ATTEMPT) {
                employeeService.disable(login);
                return ResponseEntity.badRequest()
                        .body(MESSAGE_WHEN_USER_BLOCKED);
            }
        }
        return ResponseEntity.badRequest()
                .body(NOT_CORRECT);
    }
}
