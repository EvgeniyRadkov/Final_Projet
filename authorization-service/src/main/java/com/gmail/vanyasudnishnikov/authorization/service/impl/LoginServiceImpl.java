package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.service.LoginService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Slf4j
@AllArgsConstructor
public class LoginServiceImpl implements LoginService {
    private static final String REGEX = "\\w+([\\.-]?\\w+)*@\\w+([\\.-]?\\w+)*\\.\\w{2,4}";
    private EmployeeRepository employeeRepository;


    @Override
    @Transactional
    public Boolean isUsername(String login) {
        Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
        if (optionalEmployee.isEmpty()) {
            return false;
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean isUserMail(String login) {
        Pattern pattern = Pattern.compile(REGEX);
        Matcher matcher = pattern.matcher(login);
        boolean matches = matcher.matches();
        if (matches) {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            return optionalEmployee.isPresent();
        }
        return false;
    }
}
