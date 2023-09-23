package com.springsecurity.template.security.validator;

import com.springsecurity.template.security.impl.ConstraintViolation;
import com.springsecurity.template.security.validator.anno.ValidFullName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;

@Component
public class FullNameValidator implements ConstraintValidator<ValidFullName, String>, ConstraintViolation {

    private String lengthMessage;
    private String numbersMessage;

    @Override
    public void initialize(ValidFullName constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.numbersMessage = constraintAnnotation.numbersMessage();
    }

    @Override
    public boolean isValid(String fullName, ConstraintValidatorContext constraintValidatorContext) {

        if (fullName.length() <= 2) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (!fullName.matches("^[a-zA-Z\\s]+$")) {
            constraintViolation(constraintValidatorContext, numbersMessage);
            return false;
        }
        return true;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}