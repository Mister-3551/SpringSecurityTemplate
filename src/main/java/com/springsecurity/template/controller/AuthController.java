package com.springsecurity.template.controller;

import com.springsecurity.template.record.signInRequest;
import com.springsecurity.template.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final TokenService tokenService;
    private final AuthenticationManager authenticationManager;

    public AuthController(TokenService tokenService, AuthenticationManager authenticationManager) {
        this.tokenService = tokenService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/sign-in")
    public String tokenByBody(Authentication authentication) {
        LOG.debug("Token request for user: '{}'", authentication);
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token: {}", token);
        return token;
    }

    @PostMapping("/sign-in-by-body")
    public String tokenByBody(@RequestBody signInRequest signInRequest) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInRequest.usernameOrEmailAddress(), signInRequest.password()));
        LOG.debug("Token request for user: '{}'", authentication);
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token: {}", token);
        return token;
    }
}