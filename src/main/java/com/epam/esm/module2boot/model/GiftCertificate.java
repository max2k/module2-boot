package com.epam.esm.module2boot.model;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
public class GiftCertificate {
    private final int INT_NO_VAL = -1;
    int id = INT_NO_VAL;
    String description;
    String name;
    BigDecimal price;
    int duration = INT_NO_VAL;

    Date createDate;
    Date lastUpdateDate;

    Set<Tag> tags;

}
