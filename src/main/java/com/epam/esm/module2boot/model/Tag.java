package com.epam.esm.module2boot.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;


@Data
@Entity
//@Table(name="tag")
public class Tag {
    public static final int NO_ID = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = NO_ID;

    String name;

    public boolean isNoId() {
        return id == NO_ID;
    }
}
