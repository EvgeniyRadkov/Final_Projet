package com.gmail.vanyasudnishnikov.authorization.service.validator;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;


@AllArgsConstructor
public class IsExistsUserValidator implements ConstraintValidator<IsExistsUser, String> {
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public boolean isValid(String login, ConstraintValidatorContext constraintValidatorContext) {
        Optional<Employee> optionalEmployee = employeeRepository.findByName(login);
        if (optionalEmployee.isPresent()) {
            return true;

        }
        Optional<Employee> employeeByUserMail = employeeRepository.findByUserMail(login);
        if (employeeByUserMail.isPresent()) {
            return true;
        }
        return false;
    }
}
