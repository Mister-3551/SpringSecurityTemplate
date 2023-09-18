package com.springsecurity.template.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.io.Serial;
import java.time.LocalDate;
import java.util.Collection;

@Getter
@Setter
public class CustomUser extends User {

    @Serial
    private static final long serialVersionUID = 1L;

    public CustomUser(String fullName, String username, String password, String emailAddress, Collection<? extends GrantedAuthority> authorities, LocalDate birthdate) {
        super(username, password, authorities);
        this.fullName = fullName;
        this.emailAddress = emailAddress;
        this.birthdate = birthdate;
    }

    private String fullName;
    private String emailAddress;
    private LocalDate birthdate;

    @Override
    public String toString() {
        return "User full name = " + fullName + ", email Address = " + emailAddress + ", birthdate=" + birthdate
                + "] " + super.toString();
    }
}