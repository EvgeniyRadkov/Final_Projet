package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.service.LoginService;
import com.gmail.vanyasudnishnikov.authorization.service.util.JwtUtil;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeSessionRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.TokenBlacklistRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.model.EmployeeSession;
import com.gmail.vanyasudnishnikov.authorization.repository.model.InvalidToken;
import com.gmail.vanyasudnishnikov.authorization.service.JwtTokenService;
import com.gmail.vanyasudnishnikov.authorization.repository.model.JwtToken;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@Slf4j
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {
    private static final String PATTERN_FOR_DATE = "hh.mm dd.MM.yyyy";
    private JwtUtil jwtUtil;
    private LoginService loginService;
    private EmployeeRepository employeeRepository;
    private EmployeeSessionRepository employeeSessionRepository;
    private TokenBlacklistRepository tokenBlacklistRepository;

    @Override
    @Transactional
    public String createByUsername(String login) throws IllegalArgumentException {
        Boolean username = loginService.isUsername(login);
        if (username) {
            JwtToken jwtToken = jwtUtil.generateJwtToken(login);
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeSession employeeSession = createEmployeeSession(jwtToken, employee);
            employeeSessionRepository.add(employeeSession);
            return jwtToken.getJwtToken();
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            String employeeUsername = employee.getUsername();
            JwtToken jwtToken = jwtUtil.generateJwtToken(employeeUsername);
            EmployeeSession employeeSession = createEmployeeSession(jwtToken, employee);
            employeeSessionRepository.add(employeeSession);
            return jwtToken.getJwtToken();
        }
    }

    @Override
    @Transactional
    public Boolean closeByUsername(String username) {
        Optional<Employee> optionalEmployee = employeeRepository.findByName(username);
        if (optionalEmployee.isEmpty()) {
            return false;
        }
        Employee employee = optionalEmployee.get();
        Set<EmployeeSession> employeeSessions = employee.getEmployeeSession();
        List<String> jwtTokenList = new ArrayList<>();
        String formattedDate = getDateNowInFormat();
        for (EmployeeSession employeeSession : employeeSessions) {
            employeeSession.setSessionEnd(formattedDate);
            String jwtToken = employeeSession.getJwtToken();
            jwtTokenList.add(jwtToken);
            Boolean update = employeeSessionRepository.update(employeeSession);
            if (!update) {
                throw new IllegalArgumentException();
            }
        }
        for (String token : jwtTokenList) {
            InvalidToken invalidToken = new InvalidToken();
            invalidToken.setJwtToken(token);
            invalidToken.setClosingDate(formattedDate);
            Optional<InvalidToken> addedToken = tokenBlacklistRepository.add(invalidToken);
            if (addedToken.isEmpty()) {
                throw new IllegalArgumentException();
            }
        }
        return true;
    }

    @Override
    @Transactional
    public Boolean isClosed(String jwtToken) {
        Optional<InvalidToken> optionalToken = tokenBlacklistRepository.findByName(jwtToken);
        boolean validateJwtToken = jwtUtil.validateJwtToken(jwtToken);
        if (!validateJwtToken) {
            return false;
        }
        return optionalToken.isPresent();
    }

    @Override
    @Transactional
    public Boolean close(String jwtToken) {
        InvalidToken invalidToken = new InvalidToken();
        invalidToken.setJwtToken(jwtToken);
        String formattedDate = getDateNowInFormat();
        invalidToken.setClosingDate(formattedDate);
        Optional<InvalidToken> addedToken = tokenBlacklistRepository.add(invalidToken);
        return addedToken.isPresent();
    }

    private String getDateNowInFormat() {
        LocalDateTime nowDate = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(PATTERN_FOR_DATE);
        return nowDate.format(formatter);
    }

    private EmployeeSession createEmployeeSession(JwtToken jwtToken, Employee employee) {
        EmployeeSession employeeSession = new EmployeeSession();
        employeeSession.setEmployee(employee);
        String token = jwtToken.getJwtToken();
        employeeSession.setJwtToken(token);
        Date sessionEnd = jwtToken.getSessionEnd();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(PATTERN_FOR_DATE);
        String sessionEndString = simpleDateFormat.format(sessionEnd);
        employeeSession.setSessionEnd(sessionEndString);
        Date sessionStart = jwtToken.getSessionStart();
        String sessionStartString = simpleDateFormat.format(sessionStart);
        employeeSession.setSessionStart(sessionStartString);
        return employeeSession;
    }
}
