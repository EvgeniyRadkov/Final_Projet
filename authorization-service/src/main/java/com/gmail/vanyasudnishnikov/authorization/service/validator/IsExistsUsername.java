package com.gmail.vanyasudnishnikov.authorization.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsExistsUsernameValidator.class)
public @interface IsExistsUsername {
    String message() default "The username is not exist or you are using non-Latin uppercase letters.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
