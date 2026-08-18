package com.ktsr.drinkIt.messaging;

import com.ktsr.drinkIt.DTO.PaymentEventDto;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendPaymentSuccessEvent(PaymentEventDto dto) {

        rabbitTemplate.convertAndSend(
                "payment-queue",
                dto);
    }
}