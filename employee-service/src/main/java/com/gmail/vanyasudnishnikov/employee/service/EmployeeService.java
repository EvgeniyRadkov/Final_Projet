package com.gmail.vanyasudnishnikov.employee.service;

import com.gmail.vanyasudnishnikov.employee.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;

import java.util.Optional;

public interface EmployeeService {
    Optional<Long> add(EmployeeDTO employeeDTO);

    Optional<ViewEmployeeDTO> get(Long employeeId);
}
