package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.Data;
import net.minidev.json.annotate.JsonIgnore;

@Data
@Entity
@Table(name = "roles")
public class Role {
    public static final int NO_ID = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private int id = NO_ID;
    private String name;

    @JsonIgnore
    boolean isNoId() {
        return id == NO_ID;
    }
}
