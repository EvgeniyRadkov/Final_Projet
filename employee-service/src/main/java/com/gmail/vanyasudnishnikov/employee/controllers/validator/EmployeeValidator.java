package com.gmail.vanyasudnishnikov.employee.controllers.validator;

import com.gmail.vanyasudnishnikov.employee.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.employee.repository.model.Employee;
import com.gmail.vanyasudnishnikov.employee.service.model.EmployeeDTO;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
@AllArgsConstructor
public class EmployeeValidator implements Validator {
    private EmployeeRepository companyRepository;

    @Override
    public boolean supports(Class<?> clazz) {
        return false;
    }

    @SneakyThrows
    @Override
    public void validate(Object target, Errors errors) {
        EmployeeDTO employeeDTO = (EmployeeDTO) target;
        String name = employeeDTO.getFullNameIndividual();
        String ibanByn = employeeDTO.getPersonIbanByn();
        String ibanCurrency = employeeDTO.getPersonIbanCurrency();
        List<Employee> employees = companyRepository.find(name, ibanByn, ibanCurrency);
        if (!employees.isEmpty()) {
            errors.reject("error");
        }
        String recruitmentDate = employeeDTO.getRecruitmentDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date recruitment = format.parse(recruitmentDate);
        Date date = new Date();
        if (recruitment.after(date)) {
            errors.reject("error");
        }
        String terminationDate = employeeDTO.getTerminationDate();
        Date termination = format.parse(terminationDate);
        if (termination.before(date)) {
            errors.reject("error");
        }
        if (recruitment.after(termination)) {
            errors.reject("error");
        }
    }
}
