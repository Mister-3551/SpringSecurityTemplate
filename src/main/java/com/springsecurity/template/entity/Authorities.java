package com.springsecurity.template.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
public class Authorities {

    @Id
    private Integer id;
    private String authority;

    public Authorities() {}
}