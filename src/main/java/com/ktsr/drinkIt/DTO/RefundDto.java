package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RefundDto {

    @NotNull(message = "Payment Id is required")
    private Long paymentId;

    @NotNull(message = "Refund amount is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double amount;

    @NotBlank(message = "Refund reason is required")
    private String reason;
}