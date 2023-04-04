package com.epam.esm.module2boot.model;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
public class GiftCertificate {

    public static final int INT_NO_VAL = -1;

    @Builder.Default
    int id = INT_NO_VAL;

    String description;
    String name;
    BigDecimal price;

    @Builder.Default
    int duration = INT_NO_VAL;

    Date createDate;
    Date lastUpdateDate;

    Set<Tag> tags;

}
