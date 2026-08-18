package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.PaymentDto;
import com.ktsr.drinkIt.DTO.PaymentEventDto;
import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.entity.User;
import com.razorpay.PaymentLink;
import com.razorpay.RazorpayException;
import com.stripe.exception.StripeException;

public interface PaymentService {

    Payment createPayment(PaymentDto dto) throws RazorpayException, StripeException;

    Payment getPaymentOrderById(Long id);

    Payment getPaymentOrderByPaymentId(String paymentId);

    String createStripePaymentLink(User user, Double amount, Long id) throws StripeException;

    PaymentLink createRazorPayPaymentLink(User user, Double amount, Long id) throws RazorpayException;

    Boolean processedPayment(Payment payment, String paymentId, String paymentLinkId) throws RazorpayException;

    void processPaymentEvent(PaymentEventDto event);
}
