package com.ktsr.drinkIt.DTO;

import com.ktsr.drinkIt.enums.PaymentMethod;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentDto {

    @NotNull(message = "Order Id is required")
    private Long orderId;

    @NotNull(message = "Payment Method is required")
    private PaymentMethod paymentMethod;
}