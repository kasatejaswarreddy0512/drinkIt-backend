package com.ktsr.drinkIt.DTO;

import com.ktsr.drinkIt.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEventDto {

    private Long orderId;

    private Long userId;

    private String orderNumber;

    private PaymentStatus paymentStatus;
}