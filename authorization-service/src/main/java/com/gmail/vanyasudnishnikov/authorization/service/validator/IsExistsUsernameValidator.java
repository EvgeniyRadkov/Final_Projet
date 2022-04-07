package com.gmail.vanyasudnishnikov.authorization.service.validator;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@AllArgsConstructor
public class IsExistsUsernameValidator implements ConstraintValidator<IsExistsUsername, String> {
    private EmployeeRepository employeeRepository;

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {
        boolean canEncode = StandardCharsets.US_ASCII
                .newEncoder()
                .canEncode(username);
        boolean isUpperCase = username.codePoints()
                .filter(Character::isUpperCase)
                .findFirst()
                .isPresent();
        if (!canEncode || isUpperCase) {
            return false;
        }
        Optional<Employee> optionalEmployee = employeeRepository.findByName(username);
        if (optionalEmployee.isEmpty()) {
            return false;
        }
        return true;
    }
}
