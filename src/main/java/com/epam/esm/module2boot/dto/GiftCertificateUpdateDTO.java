package com.epam.esm.module2boot.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
public class GiftCertificateUpdateDTO {
    Map<String, String> fields;
}
