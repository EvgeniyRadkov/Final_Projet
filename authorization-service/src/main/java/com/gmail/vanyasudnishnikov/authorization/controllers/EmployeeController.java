package com.gmail.vanyasudnishnikov.authorization.controllers;

import com.gmail.vanyasudnishnikov.authorization.service.EmployeeService;
import com.gmail.vanyasudnishnikov.authorization.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.authorization.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/auth/signin", consumes = MediaType.APPLICATION_JSON_VALUE)
public class EmployeeController {
    private EmployeeService employeeService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Optional<ViewEmployeeDTO> addEmployee(@RequestBody @Validated EmployeeDTO employeeDTO,
                                                 HttpServletResponse response) {
        Optional<ViewEmployeeDTO> addedEmployee = employeeService.add(employeeDTO);
        if (addedEmployee.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Optional.empty();
        }
        return addedEmployee;
    }
}
