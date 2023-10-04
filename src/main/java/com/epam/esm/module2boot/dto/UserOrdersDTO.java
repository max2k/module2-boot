package com.epam.esm.module2boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Relation(collectionRelation = "UserOrderList", itemRelation = "UserOrder")
public class UserOrdersDTO extends RepresentationModel<UserOrdersDTO> {
    int id;
    String userFirstName;
    String userLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date giftCertificateCreateDate;
    BigDecimal cost;
}
