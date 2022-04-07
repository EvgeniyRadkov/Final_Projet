package com.gmail.vanyasudnishnikov.employee.service.impl;

import com.gmail.vanyasudnishnikov.employee.repository.EmployeeDetailsRepository;
import com.gmail.vanyasudnishnikov.employee.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.employee.repository.model.Employee;
import com.gmail.vanyasudnishnikov.employee.repository.model.EmployeeDetails;
import com.gmail.vanyasudnishnikov.employee.repository.model.PositionEnum;
import com.gmail.vanyasudnishnikov.employee.service.EmployeeService;
import com.gmail.vanyasudnishnikov.employee.repository.feign.CompanyFeign;
import com.gmail.vanyasudnishnikov.employee.service.model.EmployeeDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewCompanyDTO;
import com.gmail.vanyasudnishnikov.employee.service.model.ViewEmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class EmployeeServiceImpl implements EmployeeService {
    private static final String PATTERN_FOR_DATE = "dd/MM/yyyy";
    private EmployeeRepository employeeRepository;
    private CompanyFeign companyFeign;
    private EmployeeDetailsRepository employeeDetailsRepository;

    @Override
    @Transactional
    public Optional<Long> add(EmployeeDTO employeeDTO) {
        Employee employee;
        try {
            employee = convertToEmployee(employeeDTO);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return Optional.empty();
        }
        Optional<Employee> optionalEmployee = employeeRepository.add(employee);
        if (optionalEmployee.isEmpty()) {
            return Optional.empty();
        }
        Employee addedEmployee = optionalEmployee.get();
        EmployeeDetails employeeDetails = createEmployeeDetails(addedEmployee);
        Optional<EmployeeDetails> addedEmployeeDetails = employeeDetailsRepository.add(employeeDetails);
        if (addedEmployeeDetails.isEmpty()) {
            return Optional.empty();
        }
        Long employeeId = addedEmployee.getId();
        return Optional.ofNullable(employeeId);
    }

    @Override
    @Transactional
    public Optional<ViewEmployeeDTO> get(Long employeeId) {
        Optional<Employee> optionalEmployee = employeeRepository.findById(employeeId);
        if (optionalEmployee.isEmpty()) {
            return Optional.empty();
        }
        Employee employee = optionalEmployee.get();
        ViewEmployeeDTO viewEmployeeDTO = convertToEmployeeDTO(employee);
        return Optional.ofNullable(viewEmployeeDTO);
    }

    private ViewEmployeeDTO convertToEmployeeDTO(Employee employee) {
        Long companyId = employee.getCompanyId();
        Optional<ViewCompanyDTO> viewCompanyDTO = Optional.empty();
        try {
            viewCompanyDTO = companyFeign.getCompanyById(companyId);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        String companyName;
        if (viewCompanyDTO.isEmpty()) {
            companyName = "-";
        } else {
            companyName = viewCompanyDTO.get().getNameLegal();
        }
        return ViewEmployeeDTO.builder()
                .employeeId(employee.getId())
                .fullNameIndividual(employee.getNameEmployee())
                .recruitmentDate(String.valueOf(employee.getRecruitmentDate()))
                .terminationDate(String.valueOf(employee.getTerminationDate()))
                .nameLegal(companyName)
                .personIbanByn(employee.getIbanByBYN())
                .personIbanCurrency(employee.getIbanByCurrency())
                .build();
    }

    private EmployeeDetails createEmployeeDetails(Employee addedEmployee) {
        EmployeeDetails employeeDetails = new EmployeeDetails();
        employeeDetails.setEmployee(addedEmployee);
        Date date = new Date();
        employeeDetails.setCreateDate(date);
        employeeDetails.setUpdateDate(date);
        PositionEnum hired = PositionEnum.hired;
        employeeDetails.setPositionLegal(hired);
        employeeDetails.setNote("-");
        return employeeDetails;
    }

    private Employee convertToEmployee(EmployeeDTO employeeDTO) throws ParseException, IllegalArgumentException {
        Employee employee = new Employee();
        String nameEmployee = employeeDTO.getFullNameIndividual();
        employee.setNameEmployee(nameEmployee);
        String recruitmentDate = employeeDTO.getRecruitmentDate();
        SimpleDateFormat format = new SimpleDateFormat(PATTERN_FOR_DATE);
        Date recruitment = format.parse(recruitmentDate);
        employee.setRecruitmentDate(recruitment);
        String terminationDate = employeeDTO.getTerminationDate();
        Date termination = format.parse(terminationDate);
        employee.setTerminationDate(termination);
        String nameLegal = employeeDTO.getNameLegal();
        Optional<ViewCompanyDTO> viewCompanyDTO = companyFeign.getCompanyByName(nameLegal);
        if (viewCompanyDTO.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Long legalId = viewCompanyDTO.get().getLegalId();
        employee.setCompanyId(legalId);
        String personIbanByn = employeeDTO.getPersonIbanByn();
        employee.setIbanByBYN(personIbanByn);
        String personIbanCurrency = employeeDTO.getPersonIbanCurrency();
        employee.setIbanByCurrency(personIbanCurrency);
        return employee;
    }
}
