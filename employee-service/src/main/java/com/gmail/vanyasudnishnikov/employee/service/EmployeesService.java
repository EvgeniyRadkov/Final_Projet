package com.gmail.vanyasudnishnikov.employee.service;

import com.gmail.vanyasudnishnikov.employee.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface EmployeesService {
    List<ViewEmployeeDTO> get(PaginationDTO paginationDTO, Pageable pageable, String customizedPage);

    List<ViewEmployeeDTO> find(String nameLegal, String unp, String nameIndividual);
}
