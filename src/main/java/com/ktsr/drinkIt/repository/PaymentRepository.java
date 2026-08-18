package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.enums.PaymentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    Optional<Payment> findByOrderId(Long orderId);

    Optional<Payment> findByPaymentLinkId(String paymentLinkId);

    Optional<Payment> findByTransactionId(String transactionId);

    List<Payment> findByStatus(PaymentStatus status);

    boolean existsByTransactionId(String transactionId);
}