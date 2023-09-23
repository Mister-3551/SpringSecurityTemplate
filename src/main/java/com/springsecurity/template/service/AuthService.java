package com.springsecurity.template.service;

import com.springsecurity.template.record.SignInRequest;
import com.springsecurity.template.record.SignUpRequest;
import com.springsecurity.template.repository.UsersRepository;
import com.springsecurity.template.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UsersRepository usersRepository;

    @Autowired
    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager, BCryptPasswordEncoder bCryptPasswordEncoder, UsersRepository usersRepository) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.usersRepository = usersRepository;
    }

    public String signIn(SignInRequest signInRequest) {
        String token;

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.usernameOrEmailAddress(), signInRequest.password())
            );

            token = tokenService.generateToken(authentication);

            CustomUser userDetails = (CustomUser) authentication.getPrincipal();
            if (!userDetails.isAccountConfirmed()) {
                return "Email address is not confirmed";
            }

            if (userDetails.isAccountLocked()) {
                return "Account is Locked";
            }

        } catch (BadCredentialsException ex) {
            return "Bad Credentials";
        }
        return token;
    }

    public boolean signUp(SignUpRequest signUpRequest) {
        if (usersRepository.insertUser(
                signUpRequest.fullName(),
                signUpRequest.username(),
                signUpRequest.emailAddress(),
                bCryptPasswordEncoder.encode(signUpRequest.password()),
                LocalDate.parse(signUpRequest.birthdate()),
                signUpRequest.country(),
                "generatedToken") == 1) {
            if (usersRepository.insertUserRole(signUpRequest.username()) == 1) {
                return true;
            } else {
                usersRepository.deleteUser(signUpRequest.username(), signUpRequest.emailAddress());
                return false;
            }
        }
        return false;
    }

    public Map<String, String> handleValidationException(MethodArgumentNotValidException methodArgumentNotValidException) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> globalErrors = methodArgumentNotValidException.getBindingResult().getGlobalErrors();
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();

        for (ObjectError error : globalErrors) {
            errors.put("global", error.getDefaultMessage());
        }

        for (FieldError error : fieldErrors) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        return errors;
    }
}