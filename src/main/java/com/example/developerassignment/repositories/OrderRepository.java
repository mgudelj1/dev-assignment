package com.example.developerassignment.repositories;

import com.example.developerassignment.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query(value = "SELECT * FROM orders WHERE order_time between ?1 and ?2", nativeQuery = true)
    List<Order> findByDateRange(LocalDateTime dateStart, LocalDateTime dateEnd);

    @Query(value = "SELECT * FROM orders o join order_item oi on o.id = oi.orders_id join product p on p.id = oi.product_id ", nativeQuery = true)
    List<Order> findAllOrders();
}
