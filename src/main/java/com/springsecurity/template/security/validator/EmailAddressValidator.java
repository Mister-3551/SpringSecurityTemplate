package com.springsecurity.template.security.validator;

import com.springsecurity.template.entity.User;
import com.springsecurity.template.repository.UsersRepository;
import com.springsecurity.template.security.impl.ConstraintViolation;
import com.springsecurity.template.security.validator.anno.ValidEmailAddress;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class EmailAddressValidator implements ConstraintValidator<ValidEmailAddress, String>, ConstraintViolation {

    private Pattern pattern;
    private Matcher matcher;
    private final UsersRepository usersRepository;
    private String accountExists;

    @Autowired
    public EmailAddressValidator(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public void initialize(ValidEmailAddress constraintAnnotation) {
        this.accountExists = constraintAnnotation.emailAddressExists();
    }

    @Override
    public boolean isValid(String emailAddress, ConstraintValidatorContext constraintValidatorContext){

        if (!validateEmail(emailAddress)) {
            return false;
        }

        Optional<User> userByEmailAddress = usersRepository.findByUsernameOrEmailAddress(emailAddress);

        if (userByEmailAddress.isPresent()) {
            User user = userByEmailAddress.get();
            if (user.getEmailAddress().equals(emailAddress)) {
                constraintViolation(constraintValidatorContext, accountExists);
                return false;
            }
        }
        return true;
    }

    @Override
    public void constraintViolation(ConstraintValidatorContext constraintValidatorContext, String violation) {
        ConstraintViolation.super.constraintViolation(constraintValidatorContext, violation);
    }

    private boolean validateEmail(String emailAddress) {
        pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
        matcher = pattern.matcher(emailAddress);
        return matcher.matches();
    }
}