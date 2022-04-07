package com.gmail.radzkovevgeni.legal.controllers;

import com.gmail.radzkovevgeni.legal.service.CompaniesService;
import com.gmail.radzkovevgeni.legal.service.model.FindCompanyDTO;
import com.gmail.radzkovevgeni.legal.service.model.PaginationDTO;
import com.gmail.radzkovevgeni.legal.service.model.ViewCompanyDTO;
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
@RequestMapping(value = "/api/legals")
public class CompaniesController {
    private CompaniesService companiesService;

    @GetMapping(params = {"pagination", "page"})
    public List<ViewCompanyDTO> getAll(@Validated @ModelAttribute PaginationDTO paginationDTO,
                                       @PageableDefault(sort = {"legalId"}, size = 10, direction = Sort.Direction.DESC) Pageable pageable,
                                       HttpServletResponse response) {
        List<ViewCompanyDTO> viewCompaniesDTO = companiesService.getList(paginationDTO, pageable);
        if (viewCompaniesDTO.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.emptyList();
        } else {
            return viewCompaniesDTO;
        }
    }

    @GetMapping
    public List<ViewCompanyDTO> findByParameters(@ModelAttribute FindCompanyDTO findCompanyDTO,
                                                 HttpServletResponse response) {
        List<ViewCompanyDTO> viewCompanyDTOList = companiesService.find(findCompanyDTO);
        if (viewCompanyDTOList.isEmpty()) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            return Collections.emptyList();
        } else {
            return viewCompanyDTOList;
        }
    }
}
