package com.gmail.vanyasudnishnikov.authorization.service;

import com.gmail.vanyasudnishnikov.authorization.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.authorization.service.model.ViewEmployeeDTO;

import java.util.Optional;

public interface EmployeeService {
    Optional<ViewEmployeeDTO> add(EmployeeDTO employeeDTO);

    Boolean disable(String login);
}
