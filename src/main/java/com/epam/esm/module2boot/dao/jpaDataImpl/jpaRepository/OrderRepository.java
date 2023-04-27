package com.epam.esm.module2boot.dao.jpaDataImpl.jpaRepository;

import com.epam.esm.module2boot.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface OrderRepository extends JpaRepository<Order, Integer>, PagingAndSortingRepository<Order, Integer> {

    Page<Order> findAllByUserId(int id, Pageable pageable);

    @Query("SELECT o.user.id FROM Order o GROUP BY o.user.id ORDER BY SUM(o.cost) DESC LIMIT 1")
    int getUserWithHighestCostOfAllOrders();
}
