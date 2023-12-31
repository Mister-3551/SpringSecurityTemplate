package com.springsecurity.template.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @GetMapping("/")
    public String home(Authentication authentication) {
        return "Spring Boot Tutorial Security JWT! - Username: " + authentication.getName() + " - " + authentication.getAuthorities();
    }

    @GetMapping("/user")
    public String user(Authentication authentication) {
        return "Welcome on the User page! - Username or email address: " + authentication.getName() + " - " + authentication.getAuthorities();
    }

    @GetMapping("/admin")
    public String admin(Authentication authentication) {
        return "Welcome on the Admin page! - Username or email address: " + authentication.getName() + " - " + authentication.getAuthorities();
    }
}