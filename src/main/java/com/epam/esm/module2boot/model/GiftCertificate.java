package com.epam.esm.module2boot.model;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    //@Builder.Default
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String description;
    String name;
    BigDecimal price;

    // @Builder.Default
    int duration;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date createDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date lastUpdateDate;

    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    })
//    @ManyToMany
    @JoinTable(
            name = "cert_tag",
            joinColumns = @JoinColumn(name = "cert_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    Set<Tag> tags;

}
