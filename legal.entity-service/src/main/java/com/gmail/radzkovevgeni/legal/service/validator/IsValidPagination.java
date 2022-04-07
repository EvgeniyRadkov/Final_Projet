package com.gmail.radzkovevgeni.legal.service.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.LOCAL_VARIABLE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PaginationValidator.class)
public @interface IsValidPagination {
    String message() default "Pagination is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
