package com.epam.esm.module2boot.dao.jpaDataImpl;

import com.epam.esm.module2boot.dao.OrderDAO;
import com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository.OrderRepository;
import com.epam.esm.module2boot.exception.BadRequestException;
import com.epam.esm.module2boot.exception.NotFoundException;
import com.epam.esm.module2boot.model.Order;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
@Profile("jpa-data")
@AllArgsConstructor
public class OrderDaoImpl implements OrderDAO {

    private final OrderRepository orderRepository;


    @Override
    public Order createOrder(Order order) {

        if (orderRepository.existsById(order.getId())) {
            throw new BadRequestException("Order with id:" + order.getId() + " already exist");
        }

        try {
            return orderRepository.save(order);
        } catch (DataIntegrityViolationException e) {
            throw new BadRequestException("Order could not be created, possible reasons: order with this " +
                    "certificate already exists for this user", e);
        }

    }

    @Override
    public Page<Order> getOrderListByUserId(int id, int pageNum, int pageSize) {
        return orderRepository.findAllByUserId(id, PageRequest.of(pageNum, pageSize, Sort.unsorted()));
    }

    @Override
    public Order getOrder(Integer id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Order with id:" + id + " not found"));
    }

    @Override
    public int getUserIDWithHighestCostOfAllOrders() {
        return orderRepository.getUserWithHighestCostOfAllOrders();
    }
}
