package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {

    Optional<Order> findByOrderNumber(String orderNumber);

    List<Order> findByUserId(Long userId);

    List<Order> findByStatus(OrderStatus status);

    List<Order> findByPaymentStatus(PaymentStatus paymentStatus);

    boolean existsByOrderNumber(String orderNumber);
}