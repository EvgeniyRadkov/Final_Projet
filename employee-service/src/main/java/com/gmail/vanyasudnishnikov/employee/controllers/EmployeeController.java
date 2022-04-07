package com.gmail.vanyasudnishnikov.employee.controllers;

import com.gmail.vanyasudnishnikov.employee.controllers.validator.EmployeeValidator;
import com.gmail.vanyasudnishnikov.employee.service.EmployeeService;
import com.gmail.vanyasudnishnikov.employee.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/employees")
public class EmployeeController {
    private static final String MESSAGE_WHEN_PARAMETERS_ARE_NOT_VALID = "неверно заданы параметры";
    private static final String MESSAGE_IF_EMPLOYEE_EXISTS = "Сотрудник существует";
    private static final String MESSAGE_IF_EMPLOYEE_IS_CREATED = "Сотрудник успешно создан";
    private EmployeeService employeeService;
    private EmployeeValidator employeeValidator;

    @PostMapping
    public Optional<Long> add(@Validated @RequestBody EmployeeDTO employeeDTO,
                              BindingResult bindingResult,
                              HttpServletResponse response) throws IOException {

        if (bindingResult.hasErrors()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST,
                    MESSAGE_WHEN_PARAMETERS_ARE_NOT_VALID);
            return Optional.empty();
        }
        System.out.println(employeeDTO);
        employeeValidator.validate(employeeDTO, bindingResult);
        if (bindingResult.hasErrors()) {
            response.sendError(HttpServletResponse.SC_CONFLICT,
                    MESSAGE_IF_EMPLOYEE_EXISTS
            );
            return Optional.empty();
        }
        Optional<Long> employeeId = employeeService.add(employeeDTO);
        if (employeeId.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Optional.empty();
        }
        response.sendError(HttpServletResponse.SC_OK,
                MESSAGE_IF_EMPLOYEE_IS_CREATED);
        return employeeId;
    }

    @GetMapping(params = {"employee_id"})
    public Optional<ViewEmployeeDTO> get(@RequestParam(name = "employee_id") Long employeeId,
                                         HttpServletResponse response) {
        Optional<ViewEmployeeDTO> viewEmployeeDTO = employeeService.get(employeeId);
        if (viewEmployeeDTO.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Optional.empty();
        } else {
            return viewEmployeeDTO;
        }
    }
}
