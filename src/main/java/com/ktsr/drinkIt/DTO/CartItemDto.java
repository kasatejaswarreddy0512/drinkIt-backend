package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartItemDto {

    @NotNull(message = "Cart Id is required")
    private Long cartId;

    @NotNull(message = "Product Variant Id is required")
    private Long productVariantId;

    @NotNull(message = "Quantity is required")
    @Min(value = 1, message = "Quantity should be at least 1")
    private Integer quantity;


}
