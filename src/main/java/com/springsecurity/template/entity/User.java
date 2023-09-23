package com.springsecurity.template.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    private Integer id;
    private String fullName;
    private String username;
    private String emailAddress;
    private String password;
    private String authorities;
    private LocalDate birthDate;
    private String country;
    private String accountConfirmed;
    private String accountLocked;
    private String unlockDate;
    private int reportsNumber;
}