package com.epam.esm.module2boot.dao;

import com.epam.esm.module2boot.model.Order;
import org.springframework.data.domain.Page;

public interface OrderDAO {
    Order createOrder(Order order);

    Page<Order> getOrderListByUserId(int id, int pageNum, int pageSize);

    Order getOrder(Integer id);

    int getUserIDWithHighestCostOfAllOrders();

}
