package com.epam.esm.module2boot.dto;

import lombok.Data;
import org.springframework.hateoas.EntityModel;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderDTO extends EntityModel<OrderDTO> {
    int id;
    int userId;
    int giftCertificateId;
    Date purchaseDate;
    BigDecimal cost;
}
