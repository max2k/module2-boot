package com.epam.esm.module2boot.model.dto;

import lombok.Data;

@Data
public class TagDTO {
    private int id;

 //   @NotBlank(message = "name is mandatory")
    private String name;
}
