package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

import java.util.Set;

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

    @Column(name = "passwd")
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @JsonIgnore
    public boolean isNoId() {
        return id == NO_ID;
    }

}
