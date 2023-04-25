package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;


@Data
@Entity
@Table(name = "tag")
public class Tag extends RepresentationModel<Tag> {
    public static final int NO_ID = -1;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = NO_ID;

    String name;

    public boolean isNoId() {
        return id == NO_ID;
    }
}
