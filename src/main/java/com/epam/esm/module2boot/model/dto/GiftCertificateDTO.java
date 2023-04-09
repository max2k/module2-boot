package com.epam.esm.module2boot.model.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class GiftCertificateDTO {
    int id;

    String description;
    String name;
    BigDecimal price;
    int duration ;

    Date createDate;
    Date lastUpdateDate;

    Set<TagDTO> tags;

}
