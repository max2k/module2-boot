package com.epam.esm.module2boot.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class GiftCertificateQueryDTO {
    Map<String, Object> queryFields;
    List<String> sorting;
}
