package com.gmail.vanyasudnishnikov.authorization.service.validator;

import com.gmail.vanyasudnishnikov.authorization.repository.EmployeeRepository;
import com.gmail.vanyasudnishnikov.authorization.repository.model.Employee;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.charset.StandardCharsets;
import java.util.Optional;


@AllArgsConstructor
public class UniqueUsernameValidator implements ConstraintValidator<UniqueUsername, String> {
    private EmployeeRepository employeeRepository;

    @Override
    @Transactional
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
            return true;
        }
        return false;
    }
}
