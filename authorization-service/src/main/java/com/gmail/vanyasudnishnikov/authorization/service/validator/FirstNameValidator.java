package com.gmail.vanyasudnishnikov.authorization.service.validator;

import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class FirstNameValidator implements ConstraintValidator<InCyrillic, String> {

    @Override
    public boolean isValid(String firstName, ConstraintValidatorContext constraintValidatorContext) {
        boolean inCyrillic = firstName.chars()
                .mapToObj(Character.UnicodeBlock::of)
                .allMatch(Character.UnicodeBlock.CYRILLIC::equals);
        return inCyrillic;
    }
}
