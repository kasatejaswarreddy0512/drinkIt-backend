package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductImageDto {

    @NotNull(message = "Product Id is required")
    private Long productId;

    @NotBlank(message = "Image URL is required")
    @Size(max = 500, message = "Image URL cannot exceed 500 characters")
    private String imageUrl;

    @Builder.Default
    private Boolean primaryImage = false;

    @Builder.Default
    private Boolean active = true;
}
