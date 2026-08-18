package com.ktsr.drinkIt.consumer;


import com.ktsr.drinkIt.DTO.NotificationDto;
import com.ktsr.drinkIt.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventConsumer {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-queue")
    public void consume(NotificationDto dto) {

        notificationService.createNotification(dto);

    }
}