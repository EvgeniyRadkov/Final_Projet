package com.gmail.radzkovevgeni.legal.service.validator;

import com.gmail.radzkovevgeni.legal.service.model.PaginationEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Slf4j
public class PaginationValidator implements ConstraintValidator<IsValidPagination, String> {

    @Override
    public boolean isValid(String pagination, ConstraintValidatorContext constraintValidatorContext) {
        try {
            PaginationEnum paginationEnum = PaginationEnum.valueOf(pagination);
            switch (paginationEnum) {
                case Default:
                    return true;
                case Customed:
                    return true;
            }
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            return false;
        }
        return false;
    }
}
