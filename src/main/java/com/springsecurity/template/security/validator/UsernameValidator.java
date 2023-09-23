package com.springsecurity.template.security.validator;

import com.springsecurity.template.entity.User;
import com.springsecurity.template.repository.UsersRepository;
import com.springsecurity.template.security.impl.ConstraintViolation;
import com.springsecurity.template.security.validator.anno.ValidUsername;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UsernameValidator implements ConstraintValidator<ValidUsername, String>, ConstraintViolation {

    private final UsersRepository usersRepository;
    private String lengthMessage;
    private String uppercaseMessage;
    private String specialCharacterMessage;
    private String usernameExists;

    @Autowired
    public UsernameValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(ValidUsername constraintAnnotation) {
        this.lengthMessage = constraintAnnotation.lengthMessage();
        this.uppercaseMessage = constraintAnnotation.uppercaseMessage();
        this.specialCharacterMessage = constraintAnnotation.specialCharactersMessage();
        this.usernameExists = constraintAnnotation.usernameExists();
    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext constraintValidatorContext) {

        if (username.length() < 4 || username.length() > 16) {
            constraintViolation(constraintValidatorContext, lengthMessage);
            return false;
        }

        if (username.matches(".*[A-Z].*")) {
            constraintViolation(constraintValidatorContext, uppercaseMessage);
            return false;
        }

        if (username.matches(".*[!@#$%^&*()].*")) {
            constraintViolation(constraintValidatorContext, specialCharacterMessage);
            return false;
        }

        Optional<User> userByUsername = usersRepository.findByUsernameOrEmailAddress(username);

        if (userByUsername.isPresent()) {
            User user = userByUsername.get();
            if (user.getUsername().equals(username)) {
                constraintViolation(constraintValidatorContext, usernameExists);
                return false;
            }
        }
        return username.matches("^[a-z0-9]+$");
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }
}