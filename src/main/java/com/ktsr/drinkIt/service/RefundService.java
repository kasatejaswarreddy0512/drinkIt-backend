package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.RefundDto;
import com.ktsr.drinkIt.entity.Refund;
import com.ktsr.drinkIt.enums.RefundStatus;

import java.util.List;

public interface RefundService {

    Refund createRefund(RefundDto dto);

    Refund updateRefund(Long id, RefundDto dto);

    Refund getRefundById(Long id);

    List<Refund> getAllRefunds();

    List<Refund> getRefundsByPayment(Long paymentId);

    List<Refund> getRefundsByStatus(RefundStatus status);

    boolean existsById(Long id);

    void deleteRefund(Long id);
}
