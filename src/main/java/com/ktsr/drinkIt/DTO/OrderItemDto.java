package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDto {

    @NotNull(message = "Order Id is required")
    private Long orderId;

    @NotNull(message = "Product Variant Id is required")
    private Long productVariantId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be greater than zero")
    private Integer quantity;
}