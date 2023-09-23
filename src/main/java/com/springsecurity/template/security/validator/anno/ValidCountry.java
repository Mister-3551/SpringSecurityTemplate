package com.springsecurity.template.security.validator.anno;

import com.nimbusds.jose.Payload;
import com.springsecurity.template.security.validator.CountryValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = CountryValidator.class)
@Documented
public @interface ValidCountry {

    String message() default "Country must exists";
    String numbersMessage() default "Country must not contains numbers";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}