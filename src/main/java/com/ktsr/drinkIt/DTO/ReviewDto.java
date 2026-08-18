package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.*;
import lombok.*;

@Getter @Data
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReviewDto {

    @NotNull(message = "User Id is required")
    private Long userId;

    @NotNull(message = "Product Id is required")
    private Long productId;

//    @NotNull(message = "Order Id is required")
//    private Long orderId;

    @NotNull(message = "Rating is required")
    @Min(value = 1, message = "Rating should be between 1 and 5")
    @Max(value = 5, message = "Rating should be between 1 and 5")
    private Double rating;

    @NotBlank(message = "Review is required")
    private String review;
}
