package com.springsecurity.template.security.validator.anno;

import com.nimbusds.jose.Payload;
import com.springsecurity.template.security.validator.EmailAddressValidator;
import jakarta.validation.Constraint;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({TYPE, FIELD, ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = EmailAddressValidator.class)
@Documented
public @interface ValidEmailAddress {

    String message() default "Invalid email address pattern";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}