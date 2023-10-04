package com.epam.esm.module2boot.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class LoginResponseDTO {
    private String accessToken;
    private String userLogin;
    private String fullName;
    //private String refreshToken;
}
