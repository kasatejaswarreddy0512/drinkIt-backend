package com.ktsr.drinkIt.messaging;

import com.ktsr.drinkIt.DTO.OrderEventDto;
import com.ktsr.drinkIt.entity.Order;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderEventProducer {

    private final RabbitTemplate rabbitTemplate;

    public void sendOrderCreatedEvent(OrderEventDto orderEventDto) {

        rabbitTemplate.convertAndSend(
                "order-queue",
                orderEventDto);
    }

    public void sendOrderCancelledEvent(OrderEventDto orderEventDto) {
        rabbitTemplate.convertAndSend("order-queue", orderEventDto);
    }
}