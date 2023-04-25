package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usertable")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String firstName;
    private String lastName;

    private String email;

}
