package com.epam.esm.module2boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TagDTO {
    private int id;

    //   @NotBlank(message = "name is mandatory")
    private String name;
}
