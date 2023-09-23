package com.springsecurity.template.record;

public record SignInRequest(String usernameOrEmailAddress, String password) {

}