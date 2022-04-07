package com.gmail.vanyasudnishnikov.employee.service.impl;

import com.gmail.vanyasudnishnikov.employee.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.employee.repository.model.Employee;
import com.gmail.vanyasudnishnikov.employee.service.EmployeesService;
import com.gmail.vanyasudnishnikov.employee.repository.feign.CompanyFeign;
import com.gmail.vanyasudnishnikov.employee.service.model.PaginationDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.PaginationEnum;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewCompanyDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeesServiceImpl implements EmployeesService {
    private static final Integer MIN_CUSTOM_PAGE = 20;
    private static final Integer AVERAGE_CUSTOM_PAGE = 50;
    private static final Integer MAX_CUSTOM_PAGE = 100;
    private static final String DATE_FORMAT = "dd/MM/yyyy";
    private EmployeeRepository employeeRepository;
    private CompanyFeign companyFeignService;


    @Override
    @Transactional
    public List<ViewEmployeeDTO> get(PaginationDTO paginationDTO, Pageable pageable, String customizedPage) {
        PaginationEnum pagination = paginationDTO.getPagination();
        if (pagination == PaginationEnum.Default) {
            List<Employee> companies = employeeRepository.findAll(pageable);
            if (companies.isEmpty()) {
                return Collections.emptyList();
            }
            return convertToListDTO(companies);
        } else {
            if (customizedPage == null || customizedPage.equals("")) {
                return Collections.emptyList();
            }
            int customPage = Integer.parseInt(customizedPage);
            Integer page = paginationDTO.getPage();
            if (customPage == MIN_CUSTOM_PAGE || customPage == AVERAGE_CUSTOM_PAGE || customPage == MAX_CUSTOM_PAGE) {
                Pageable customPageable = PageRequest.of(page, customPage);
                List<Employee> companies = employeeRepository.findAll(customPageable);
                if (companies.isEmpty()) {
                    return Collections.emptyList();
                }
                return convertToListDTO(companies);
            }
            return Collections.emptyList();
        }
    }

    @Override
    @Transactional
    public List<ViewEmployeeDTO> find(String nameLegal, String unp, String nameIndividual) {
        String ibanByByn = null;
        List<ViewCompanyDTO> viewCompanies = companyFeignService.findByParameters(nameLegal, unp, ibanByByn);
        if (viewCompanies.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> legalIdList = new ArrayList<>();
        for (ViewCompanyDTO viewCompany : viewCompanies) {
            Long legalId = viewCompany.getLegalId();
            legalIdList.add(legalId);
        }
        List<Employee> employees = employeeRepository.search(legalIdList, nameIndividual);
        if (employees.isEmpty()) {
            return Collections.emptyList();
        }
        return convertToListDTO(employees);
    }


    private List<ViewEmployeeDTO> convertToListDTO(List<Employee> employees) {
        List<ViewEmployeeDTO> viewEmployees = new ArrayList<>();
        for (Employee employee : employees) {
            Date recruitmentDate = employee.getRecruitmentDate();
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
            String recruitment = simpleDateFormat.format(recruitmentDate);
            Date terminationDate = employee.getTerminationDate();
            String termination = simpleDateFormat.format(terminationDate);
            Long companyId = employee.getCompanyId();
            Optional<ViewCompanyDTO> companyDTO = Optional.empty();
            try {
                companyDTO = companyFeignService.getCompanyById(companyId);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
            String companyName;
            if (companyDTO.isEmpty()) {
                companyName = "-";
            } else {
                companyName = companyDTO.get().getNameLegal();
            }
            ViewEmployeeDTO viewCompanyDTO = ViewEmployeeDTO.builder()
                    .employeeId(employee.getId())
                    .fullNameIndividual(employee.getNameEmployee())
                    .recruitmentDate(recruitment)
                    .terminationDate(termination)
                    .nameLegal(companyName)
                    .personIbanByn(employee.getIbanByBYN())
                    .personIbanCurrency(employee.getIbanByCurrency())
                    .build();
            viewEmployees.add(viewCompanyDTO);
        }
        return viewEmployees;
    }
}
