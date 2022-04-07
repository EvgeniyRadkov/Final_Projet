package com.gmail.vanyasudnishnikov.authorization.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = FirstNameValidator.class)
public @interface InCyrillic {
    String message() default "Enter your first name in Russian";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
