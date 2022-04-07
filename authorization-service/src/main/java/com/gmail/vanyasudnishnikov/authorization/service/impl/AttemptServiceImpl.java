package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeDetailsRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.model.EmployeeDetails;
import com.gmail.vanyasudnishnikov.authorization.service.AttemptService;
import com.gmail.vanyasudnishnikov.authorization.service.LoginService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AttemptServiceImpl implements AttemptService {
    private EmployeeRepository employeeRepository;
    private EmployeeDetailsRepository updateEmployeeDetailsRepository;
    private LoginService loginService;

    @Override
    @Transactional
    public Boolean addByUsername(String login) {
        Boolean isUsername = loginService.isUsername(login);
        if (isUsername) {
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            Integer attempt = employeeDetails.getAttempt();
            attempt++;
            employeeDetails.setAttempt(attempt);
            return updateEmployeeDetailsRepository.update(employeeDetails);
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            Integer attempt = employeeDetails.getAttempt();
            attempt++;
            employeeDetails.setAttempt(attempt);
            return updateEmployeeDetailsRepository.update(employeeDetails);
        }
    }

    @Override
    @Transactional
    public Boolean removeByUsername(String login) {
        Boolean isUsername = loginService.isUsername(login);
        if (isUsername) {
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            employeeDetails.setAttempt(0);
            return updateEmployeeDetailsRepository.update(employeeDetails);
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            employeeDetails.setAttempt(0);
            return updateEmployeeDetailsRepository.update(employeeDetails);
        }
    }

    public Integer get(String login) {
        Boolean isUsername = loginService.isUsername(login);
        if (isUsername) {
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            return employeeDetails.getAttempt();
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            if (optionalEmployee.isEmpty()) {
                throw new IllegalArgumentException();
            }
            Employee employee = optionalEmployee.get();
            EmployeeDetails employeeDetails = employee.getEmployeeDetails();
            return employeeDetails.getAttempt();
        }
    }
}
