package com.epam.esm.module2boot.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "ORDERTABLE")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(optional = false, targetEntity = User.class)
    private User user;

    @OneToOne(optional = false, targetEntity = GiftCertificate.class)
    @JoinColumn(name = "cert_id", referencedColumnName = "id")
    private GiftCertificate giftCertificate;

    @Column(name = "order_price")
    private BigDecimal cost;

}
