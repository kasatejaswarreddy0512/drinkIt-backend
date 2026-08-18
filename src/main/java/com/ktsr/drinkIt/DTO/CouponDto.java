package com.ktsr.drinkIt.DTO;


import com.ktsr.drinkIt.enums.DiscountType;
import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CouponDto {

    @NotBlank(message = "Coupon code is required")
    @Size(max = 50, message = "Coupon code cannot exceed 50 characters")
    private String code;

    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;

    @NotNull(message = "Discount type is required")
    private DiscountType discountType;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", inclusive = false,
            message = "Discount value must be greater than 0")
    private Double discountValue;

    @Builder.Default
    @DecimalMin(value = "0.0",
            message = "Minimum amount cannot be negative")
    private Double minimumAmount = 0.0;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    @NotNull(message = "End date is required")
    private LocalDate endDate;

    @Builder.Default
    private Boolean active = true;
}