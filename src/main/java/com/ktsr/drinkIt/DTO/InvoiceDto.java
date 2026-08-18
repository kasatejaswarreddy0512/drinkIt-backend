package com.ktsr.drinkIt.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class InvoiceDto {

    @NotNull(message = "Order Id is required")
    private Long orderId;

    private String pdfUrl;
}