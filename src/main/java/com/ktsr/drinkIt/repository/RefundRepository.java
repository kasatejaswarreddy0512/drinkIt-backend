package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Refund;
import com.ktsr.drinkIt.enums.RefundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RefundRepository extends JpaRepository<Refund, Long> {

    List<Refund> findByPaymentId(Long paymentId);

    List<Refund> findByStatus(RefundStatus status);
}