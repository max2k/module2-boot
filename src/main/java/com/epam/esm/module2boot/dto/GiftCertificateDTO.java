package com.epam.esm.module2boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Data
public class GiftCertificateDTO {
    int id = -1;
    String description;
    String name;
    BigDecimal price;
    int duration;

    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date createDate;


    @JsonFormat
            (shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date lastUpdateDate;

    Set<TagDTO> tags;

}
