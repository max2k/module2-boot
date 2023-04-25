package com.epam.esm.module2boot.service;

import com.epam.esm.module2boot.dto.OrderDTO;
import com.epam.esm.module2boot.dto.UserOrderListEntityDTO;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.Order;
import com.epam.esm.module2boot.model.Tag;
import org.springframework.data.domain.Page;

public interface OrderService {
    OrderDTO createOrder(OrderDTO orderDTO) throws BadRequestException;

    Page<UserOrderListEntityDTO> getOrderListByUserId(int id, int pageNum, int pageSize) throws NotFoundException;


    Order getOrder(Integer id);

    Tag getMostWidelyUsedTag();
}
