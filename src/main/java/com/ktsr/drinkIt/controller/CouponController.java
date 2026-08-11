package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.CouponDto;
import com.ktsr.drinkIt.entity.Coupon;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponService couponService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createCoupon(@Valid @RequestBody CouponDto coupon){
        try {
            Coupon createdCoupon = couponService.createCoupon(coupon);
            return APIResponse.get(ErrorCode.SUCCESS,createdCoupon, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateCoupon(@PathVariable Long id, @Valid @RequestBody CouponDto coupon){
        try {
            boolean exists = couponService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.COUPON_NOT_FOUND,"Coupon not found", HttpStatus.NOT_FOUND);
            }
            Coupon updatedCoupon = couponService.updateCoupon(id, coupon);
            return APIResponse.get(ErrorCode.SUCCESS,updatedCoupon, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllCoupons(){
        try {
            List<Coupon> allCoupons = couponService.getAllCoupons();
            return APIResponse.get(ErrorCode.SUCCESS,allCoupons, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/code")
    public ResponseEntity<ResponseWrapper> getCouponByCode(@RequestParam("code") String code){
        try {
            Coupon coupon = couponService.getCouponByCode(code);
            return APIResponse.get(ErrorCode.SUCCESS,coupon, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveCoupons(){
        try {
            List<Coupon> activeCoupons = couponService.getActiveCoupons();
            return APIResponse.get(ErrorCode.SUCCESS,activeCoupons, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/valid")
    public ResponseEntity<ResponseWrapper> getValidCoupons(){
        try {
            List<Coupon> validCoupons = couponService.getValidCoupons();
            return APIResponse.get(ErrorCode.SUCCESS,validCoupons, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<ResponseWrapper> activateCoupon(@PathVariable Long id){
        try {
            boolean exists = couponService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.COUPON_NOT_FOUND,"Coupon not found", HttpStatus.NOT_FOUND);
            }
            Coupon coupon = couponService.activateCoupon(id);
            return APIResponse.get(ErrorCode.SUCCESS,coupon, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ResponseWrapper> deactivateCoupon(@PathVariable Long id){
        try {
            boolean exists = couponService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.COUPON_NOT_FOUND,"Coupon not found", HttpStatus.NOT_FOUND);
            }
            Coupon coupon = couponService.deactivateCoupon(id);
            return APIResponse.get(ErrorCode.SUCCESS,coupon, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteCoupon(@PathVariable Long id){
        try {
            boolean exists = couponService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.COUPON_NOT_FOUND,"Coupon not found", HttpStatus.NOT_FOUND);
            }
            couponService.deleteCoupon(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Coupon deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
