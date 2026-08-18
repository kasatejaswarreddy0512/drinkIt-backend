package com.ktsr.drinkIt.helper;

import com.ktsr.drinkIt.entity.Order;
import com.ktsr.drinkIt.entity.Payment;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {

    private Order order;

    private Payment payment;
}
