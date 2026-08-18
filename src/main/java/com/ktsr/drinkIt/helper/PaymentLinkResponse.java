package com.ktsr.drinkIt.helper;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentLinkResponse {

    private String paymentId;

    private String paymentLinkUrl;
}
