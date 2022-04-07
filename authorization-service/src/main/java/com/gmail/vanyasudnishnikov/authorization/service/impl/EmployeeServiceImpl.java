package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeDetailsRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.EmployeeDetails;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.model.RoleEnum;
import com.gmail.vanyasudnishnikov.authorization.service.EmployeeService;
import com.gmail.vanyasudnishnikov.authorization.service.LoginService;
import com.gmail.vanyasudnishnikov.authorization.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.authorization.repository.model.StatusEnum;
import com.gmail.vanyasudnishnikov.authorization.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private PasswordEncoder passwordEncoder;
    private EmployeeRepository employeeRepository;
    private EmployeeDetailsRepository employeeDetailsRepository;
    private LoginService loginService;

    @Override
    @Transactional
    public Optional<ViewEmployeeDTO> add(EmployeeDTO employeeDTO) {
        Employee employee = convertToEmployee(employeeDTO);
        StatusEnum statusEnum = StatusEnum.ACTIVE;
        employee.setStatus(statusEnum);
        RoleEnum roleEnum = RoleEnum.ROLE_USER;
        employee.setRole(roleEnum);
        Optional<Employee> optionalEmployee = employeeRepository.add(employee);
        if (optionalEmployee.isEmpty()) {
            return Optional.empty();
        }
        EmployeeDetails employeeDetails = createEmployeeDetails(employeeDTO);
        employeeDetails.setEmployee(employee);
        Optional<EmployeeDetails> addedEmployeeDetails = employeeDetailsRepository.add(employeeDetails);
        if (addedEmployeeDetails.isEmpty()) {
            return Optional.empty();
        }
        Employee addedEmployee = optionalEmployee.get();

        ViewEmployeeDTO viewEmployeeDTO = convertToUserDTO(addedEmployee);
        return Optional.of(viewEmployeeDTO);
    }

    @Override
    @Transactional
    public Boolean disable(String login) {
        Boolean username = loginService.isUsername(login);
        if (username) {
            Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
            if (optionalEmployee.isEmpty()) {
                return false;
            }
            Employee employee = optionalEmployee.get();
            StatusEnum statusEnum = StatusEnum.DISABLED;
            employee.setStatus(statusEnum);
            return employeeRepository.update(employee);
        } else {
            Optional<Employee> optionalEmployee = employeeRepository.findByUserMail(login);
            if (optionalEmployee.isEmpty()) {
                return false;
            }
            Employee employee = optionalEmployee.get();
            StatusEnum statusEnum = StatusEnum.DISABLED;
            employee.setStatus(statusEnum);
            return employeeRepository.update(employee);
        }
    }

    private ViewEmployeeDTO convertToUserDTO(Employee employee) {
        ViewEmployeeDTO viewEmployeeDTO = new ViewEmployeeDTO();
        Long employeeId = employee.getId();
        viewEmployeeDTO.setUserId(employeeId);
        StatusEnum employeeStatus = employee.getStatus();
        String status = String.valueOf(employeeStatus);
        viewEmployeeDTO.setStatus(status);
        return viewEmployeeDTO;
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        String username = employeeDTO.getUsername();
        employee.setUsername(username);
        String password = employeeDTO.getPassword();
        String passwordEncoded = passwordEncoder.encode(password);
        employee.setPassword(passwordEncoded);
        String userMail = employeeDTO.getUsermail();
        employee.setUserMail(userMail);
        return employee;
    }

    private EmployeeDetails createEmployeeDetails(EmployeeDTO employeeDTO) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        String firstName = employeeDTO.getFirstName();
        employeeDetails.setFirstName(firstName);
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh.mm dd.MM.yyyy");
        String formattedString = now.format(formatter);
        employeeDetails.setCreationDate(formattedString);
        employeeDetails.setAttempt(0);
        return employeeDetails;

    }
}
