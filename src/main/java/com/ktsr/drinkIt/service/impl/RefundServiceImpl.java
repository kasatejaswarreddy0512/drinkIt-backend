package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.RefundDto;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.entity.Refund;
import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentStatus;
import com.ktsr.drinkIt.enums.RefundStatus;
import com.ktsr.drinkIt.repository.PaymentRepository;
import com.ktsr.drinkIt.repository.RefundRepository;
import com.ktsr.drinkIt.service.RefundService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RefundServiceImpl implements RefundService {

    private final RefundRepository refundRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Refund createRefund(RefundDto dto) {
        Payment payment = paymentRepository.findById(dto.getPaymentId())
                .orElseThrow(() ->
                        new EntityNotFoundException("Payment not found"));

        if (payment.getStatus() != PaymentStatus.SUCCESS) {
            throw new IllegalArgumentException(
                    "Refund can be processed only for successful payments.");
        }

        if (dto.getAmount() > payment.getAmount()) {
            throw new IllegalArgumentException(
                    "Refund amount cannot exceed payment amount.");
        }

        Refund refund = Refund.builder()
                .payment(payment)
                .amount(dto.getAmount())
                .reason(dto.getReason())
                .status(RefundStatus.SUCCESS)
                .refundDate(LocalDateTime.now())
                .build();

        Refund savedRefund = refundRepository.save(refund);

        payment.setStatus(PaymentStatus.REFUNDED);
        paymentRepository.save(payment);

        Order order = payment.getOrder();

        if (order != null) {
            order.setPaymentStatus(PaymentStatus.REFUNDED);
            order.setStatus(OrderStatus.CANCELLED);
        }

        // TODO:
        // Razorpay Refund API

        // TODO:
        // Stripe Refund API

        // TODO:
        // RabbitMQ Notification

        return savedRefund;
    }

    @Override
    public Refund updateRefund(Long id, RefundDto dto) {
        Refund refund= getRefundById(id);
        refund.setAmount(dto.getAmount());
        refund.setReason(dto.getReason());
        return refundRepository.save(refund);
    }

    @Override
    public Refund getRefundById(Long id) {
        return refundRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<Refund> getAllRefunds() {
        return refundRepository.findAll();
    }

    @Override
    public List<Refund> getRefundsByPayment(Long paymentId) {
        return refundRepository.findByPaymentId(paymentId);
    }

    @Override
    public List<Refund> getRefundsByStatus(RefundStatus status) {
        return refundRepository.findByStatus(status);
    }

    @Override
    public boolean existsById(Long id) {
        return refundRepository.existsById(id);
    }

    @Override
    public void deleteRefund(Long id) {
        Refund refund = getRefundById(id);
        refundRepository.delete(refund);
    }
}