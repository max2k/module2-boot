package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Set;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@AllArgsConstructor
@Entity
public class GiftCertificate {

    public static final int INT_NO_VAL = -1;

    @Builder.Default
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id = INT_NO_VAL;

    String description;
    String name;
    BigDecimal price;

    @Builder.Default
    int duration = INT_NO_VAL;

    Date createDate;
    Date lastUpdateDate;

    @ManyToMany
    @JoinTable(
            name = "cert_tag",
            joinColumns = @JoinColumn(name = "cert_id"),
            inverseJoinColumns =  @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags;

}
