package com.ktsr.drinkIt.consumer;

import com.ktsr.drinkIt.DTO.OrderEventDto;
import com.ktsr.drinkIt.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventConsumer {

    private final OrderService orderService;

    @RabbitListener(queues = "order-queue")
    public void orderUpdateListener(OrderEventDto event) {

        log.info("Order Event Received : {}", event.getOrderNumber());

        orderService.processOrder(event);
    }
}