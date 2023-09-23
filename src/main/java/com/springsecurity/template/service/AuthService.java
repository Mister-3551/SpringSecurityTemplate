package com.springsecurity.template.service;

import com.springsecurity.template.record.SignInRequest;
import com.springsecurity.template.security.CustomUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    @Autowired
    public AuthService(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
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
}