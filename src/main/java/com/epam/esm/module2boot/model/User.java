package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "usertable")
public class User {
    public static final int NO_ID = -1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id = NO_ID;

    private String firstName;
    private String lastName;

    private String email;

}
