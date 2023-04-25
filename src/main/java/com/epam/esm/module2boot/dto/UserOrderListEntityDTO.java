package com.epam.esm.module2boot.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserOrderListEntityDTO extends RepresentationModel<UserOrderListEntityDTO> {
    int id;
    String userFirstName;
    String userLastName;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'hh:mm:ss.SSS")
    Date giftCertificateCreateDate;
    BigDecimal cost;
}
