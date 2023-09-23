package com.springsecurity.template.security.validator.anno;

import com.nimbusds.jose.Payload;
import com.springsecurity.template.security.validator.FullNameValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = FullNameValidator.class)
@Documented
public @interface ValidFullName {

    String message() default "The full name must include your real first and last name";
    String lengthMessage() default "Full name must be at lest 3 characters long";
    String numbersMessage() default "Full name must not contains digits";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}