package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.service.AttemptService;
import com.gmail.vanyasudnishnikov.authorization.service.LoginService;
import com.gmail.vanyasudnishnikov.authorization.service.UserService;
import com.gmail.vanyasudnishnikov.authorization.service.model.LoginDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private EmployeeRepository employeeRepository;
    private PasswordEncoder passwordEncoder;
    private LoginService loginService;
    private AttemptService attemptService;

    @Override
    @Transactional
    public Boolean isValid(LoginDTO loginDTO) {
        String login = loginDTO.getLogin();
        String password = loginDTO.getPassword();
        Boolean isUsername = loginService.isUsername(login);
        if (isUsername) {
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            boolean present = optionalEmployee.filter(value -> passwordEncoder.matches(password, value.getPassword()))
                    .isPresent();
            if (present) {
                attemptService.removeByUsername(login);
                return true;
            } else {
                attemptService.addByUsername(login);
                return false;
            }
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            boolean present = optionalEmployee.filter(value -> passwordEncoder.matches(password, value.getPassword()))
                    .isPresent();
            if (present) {
                attemptService.removeByUsername(login);
                return true;
            } else {
                attemptService.addByUsername(login);
                return false;
            }
        }
    }
}
