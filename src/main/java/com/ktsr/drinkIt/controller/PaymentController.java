package com.ktsr.drinkIt.controller;


import com.ktsr.drinkIt.DTO.PaymentDto;
import com.ktsr.drinkIt.entity.Payment;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.PaymentService;
import com.razorpay.RazorpayException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getPaymentByOrderByID(@PathVariable Long id){
        try {
            Payment payment= paymentService.getPaymentOrderById(id);
            return APIResponse.get(ErrorCode.SUCCESS, payment, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/proceed")
    public ResponseEntity<ResponseWrapper> proceedPayment(
            @RequestParam String paymentId,
            @RequestParam String paymentLinkId) {

        try {
            Payment payment = paymentService.getPaymentOrderByPaymentId(paymentLinkId);

            if (payment == null) {
                return APIResponse.get(ErrorCode.PAYMENT_NOT_FOUND, "Payment not found", HttpStatus.NOT_FOUND);
            }
            Boolean result = paymentService.processedPayment(payment, paymentId, paymentLinkId);
            return APIResponse.get(ErrorCode.SUCCESS, result, HttpStatus.OK);

        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
