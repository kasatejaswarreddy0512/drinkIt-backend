package com.ktsr.drinkIt.messaging;

import com.ktsr.drinkIt.DTO.NotificationDto;
import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationEventProducer {

    private final RabbitTemplate rabbitTemplate;

    private static final String NOTIFICATION_QUEUE = "notification-queue";

    /**
     * Send order cancelled notification
     */
    public void sendOrderCancelledNotification(Long userId, String orderNumber) {

        NotificationDto dto = new NotificationDto();

        dto.setUserId(userId);
        dto.setTitle("Order Cancelled");
        dto.setMessage(
                "Your order " + orderNumber + " has been cancelled successfully."
        );
        dto.setType(NotificationType.ORDER);

        rabbitTemplate.convertAndSend(
                NOTIFICATION_QUEUE,
                dto
        );
    }

    /**
     * Send payment success notification
     */
    public void sendPaymentSuccessNotification(User user, Order order) {

        NotificationDto dto = new NotificationDto();

        dto.setUserId(user.getId());
        dto.setTitle("Payment Successful");
        dto.setMessage(
                "Payment for your order " + order.getOrderNumber()
                        + " has been completed successfully."
        );
        dto.setType(NotificationType.PAYMENT);

        rabbitTemplate.convertAndSend(
                NOTIFICATION_QUEUE,
                dto
        );
    }

    public void sendNotification(NotificationDto dto) {
        rabbitTemplate.convertAndSend(
                NOTIFICATION_QUEUE,
                dto
        );
    }
}

