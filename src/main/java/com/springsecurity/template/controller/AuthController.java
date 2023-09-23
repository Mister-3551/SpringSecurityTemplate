package com.springsecurity.template.controller;

import com.springsecurity.template.record.SignInRequest;
import com.springsecurity.template.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    //private static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    /*@PostMapping("/sign-in")
    public String tokenByBody(Authentication authentication) {
        LOG.debug("Token request for user: '{}'", authentication);
        String token = tokenService.generateToken(authentication);
        LOG.debug("Token: {}", token);
        return token;
    }*/

    @PostMapping("/sign-in")
    public String signIn(@RequestBody SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }

    @PostMapping("/sign-up")
    public String signUp() {
        return "Welcome on the Sign Up page";
    }

    @PostMapping("/sign-out")
    public void signOut() {
    }
}