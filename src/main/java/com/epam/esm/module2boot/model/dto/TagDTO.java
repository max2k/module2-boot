package com.epam.esm.module2boot.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TagDTO {
    private int id;

 //   @NotBlank(message = "name is mandatory")
    private String name;
}
