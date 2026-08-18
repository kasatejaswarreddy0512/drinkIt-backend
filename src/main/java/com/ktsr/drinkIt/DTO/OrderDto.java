package com.ktsr.drinkIt.DTO;


import com.ktsr.drinkIt.enums.OrderStatus;
import com.ktsr.drinkIt.enums.PaymentStatus;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDto {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Address Id is required")
    private Long addressId;

    private Long couponId;

    @Builder.Default
    @DecimalMin("0.0")
    private Double totalAmount = 0.0;

    @Builder.Default
    @DecimalMin("0.0")
    private Double discount = 0.0;

    @Builder.Default
    @DecimalMin("0.0")
    private Double deliveryCharge = 0.0;

    @Builder.Default
    @DecimalMin("0.0")
    private Double tax = 0.0;

    @Builder.Default
    @DecimalMin("0.0")
    private Double finalAmount = 0.0;

    @Builder.Default
    private OrderStatus status = OrderStatus.PLACED;

    @Builder.Default
    private PaymentStatus paymentStatus = PaymentStatus.PENDING;

    private LocalDateTime deliveryDate;
}