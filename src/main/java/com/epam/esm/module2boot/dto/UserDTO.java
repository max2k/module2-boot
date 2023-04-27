package com.epam.esm.module2boot.dto;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserDTO extends RepresentationModel<UserDTO> {
    Integer id;
    String firstName;
    String lastName;

    String email;
}
