package com.gmail.vanyasudnishnikov.employee.controllers;

import com.gmail.vanyasudnishnikov.employee.service.EmployeesService;
import com.gmail.vanyasudnishnikov.employee.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/api/employees")
public class EmployeesController {
    private EmployeesService employeesService;

    @GetMapping(params = {"pagination", "page"})
    public List<ViewEmployeeDTO> getAll(@Validated @ModelAttribute PaginationDTO paginationDTO,
                                        @RequestParam("customized_page") String customizedPage,
                                        @PageableDefault(sort = {"legalId"}, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
                                        HttpServletResponse response) {
        List<ViewEmployeeDTO> viewEmployees = employeesService.get(paginationDTO, pageable, customizedPage);
        if (viewEmployees.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.emptyList();
        } else {
            return viewEmployees;
        }
    }

    @GetMapping
    public List<ViewEmployeeDTO> findByParameters(@RequestParam("Name_Legal") String nameLegal,
                                                  @RequestParam("UNP") String unp,
                                                  @RequestParam("Full_Name_Individual") String nameIndividual,
                                                  HttpServletResponse response) {
        List<ViewEmployeeDTO> viewEmployees = employeesService.find(nameLegal, unp, nameIndividual);
        if (viewEmployees.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.emptyList();
        } else {
            return viewEmployees;
        }
    }
}
