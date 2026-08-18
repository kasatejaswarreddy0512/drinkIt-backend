package com.ktsr.drinkIt.DTO;

import com.ktsr.drinkIt.enums.PaymentMethod;
import com.ktsr.drinkIt.enums.PaymentStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentEventDto {

    private Long paymentId;

    private Long orderId;

    private Long userId;

    private Double amount;

    private String transactionId;

    private PaymentMethod paymentMethod;

    private PaymentStatus paymentStatus;
}
