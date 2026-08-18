package com.ktsr.drinkIt.consumer;

import com.ktsr.drinkIt.DTO.PaymentEventDto;
import com.ktsr.drinkIt.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class PaymentEventConsumer {

    private final PaymentService paymentService;

    @RabbitListener(queues = "payment-queue")
    public void paymentSuccessListener(PaymentEventDto event) {

        log.info("Payment Success : {}", event.getTransactionId());

        paymentService.processPaymentEvent(event);
    }
}