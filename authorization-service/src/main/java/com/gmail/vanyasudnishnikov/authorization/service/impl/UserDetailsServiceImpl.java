package com.gmail.vanyasudnishnikov.authorization.service.impl;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import com.gmail.vanyasudnishnikov.authorization.repository.model.RoleEnum;
import com.gmail.vanyasudnishnikov.authorization.repository.model.StatusEnum;
import com.gmail.vanyasudnishnikov.authorization.service.model.EmployeeSecurityDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService {
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Employee> optionalEmployee = employeeRepository.findByName(username);
        if (optionalEmployee.isEmpty()) {
            throw new UsernameNotFoundException("User was not found with username");
        }
        Employee employee = optionalEmployee.get();
        EmployeeSecurityDTO employeeSecurityDTO = convertToUserDTO(employee);
        return UserDetailsImpl
                .build(employeeSecurityDTO);
    }

    private EmployeeSecurityDTO convertToUserDTO(Employee employee) {
        Long userId = employee.getId();
        String username = employee.getUsername();
        String userPassword = employee.getPassword();
        StatusEnum statusEnum = employee.getStatus();
        String status = String.valueOf(statusEnum);
        RoleEnum role = employee.getRole();
        List<RoleEnum> roleList = Collections.singletonList(role);
        return EmployeeSecurityDTO.builder().id(userId)
                .username(username)
                .password(userPassword)
                .status(status)
                .role(roleList)
                .build();
    }
}
