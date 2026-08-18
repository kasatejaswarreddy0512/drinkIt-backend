package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

import java.math.BigDecimal;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductVariantDto {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotBlank(message = "Volume is required")
    @Size(max = 50)
    private String volume;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false)
    private Double price;

    @NotNull(message = "Stock is required")
    @Min(0)
    private Integer stock;

    @DecimalMin(value = "0.0")
    @DecimalMax(value = "100.0")
    @Builder.Default
    private Double discount = 0.0;

    @NotBlank(message = "SKU is required")
    @Size(max = 100)
    private String sku;

    @Size(max = 100)
    private String barcode;

    @Builder.Default
    private Boolean active = true;
}
