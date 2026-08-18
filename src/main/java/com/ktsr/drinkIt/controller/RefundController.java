package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.RefundDto;
import com.ktsr.drinkIt.entity.Refund;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.enums.RefundStatus;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.RefundService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/refunds")
@RequiredArgsConstructor
public class RefundController {

    private final RefundService refundService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createRefund(@RequestBody RefundDto dto){
        try {
            Refund refund= refundService.createRefund(dto);
            return APIResponse.get(ErrorCode.SUCCESS,refund,HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateRefund(@PathVariable Long id, @RequestBody RefundDto dto){
        try {
            boolean exists= refundService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.REFUND_NOT_FOUND,"Refund not found.",HttpStatus.NOT_FOUND);
            }
            Refund refund=refundService.updateRefund(id,dto);
            return APIResponse.get(ErrorCode.SUCCESS,refund,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getRefundById(@PathVariable Long id){
        try {
            boolean exists= refundService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.REFUND_NOT_FOUND,"Refund not found.",HttpStatus.NOT_FOUND);
            }
            Refund refund=refundService.getRefundById(id);
            return APIResponse.get(ErrorCode.SUCCESS,refund,HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllRefunds(){
        try {
            List<Refund> refunds= refundService.getAllRefunds();
            return APIResponse.get(ErrorCode.SUCCESS,refunds,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/payment")
    public ResponseEntity<ResponseWrapper> getRefundByPayment(@RequestParam Long paymentId){
        try {
            List<Refund> refund=refundService.getRefundsByPayment(paymentId);
            return APIResponse.get(ErrorCode.SUCCESS,refund,HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/status")
    public ResponseEntity<ResponseWrapper> getRefundStatus(@RequestParam RefundStatus status){
        try {
            List<Refund> refund= refundService.getRefundsByStatus(status);
            return APIResponse.get(ErrorCode.SUCCESS,refund,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteRefund(@PathVariable Long id){
        try {
            boolean exists= refundService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.REFUND_NOT_FOUND,"Refund not found.",HttpStatus.NOT_FOUND);
            }
            refundService.deleteRefund(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Refund deleted successfully.",HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
