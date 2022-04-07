package com.gmail.vanyasudnishnikov.application.repository.feign;

import com.gmail.vanyasudnishnikov.application.service.model.ViewEmployeeDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@FeignClient(name = "employee", url = "http://localhost:8060/api/employees")
public interface EmployeeFeign {

    @GetMapping
    Optional<ViewEmployeeDTO> getEmployeeById(@RequestParam("employee_id") Long employeeId);
}