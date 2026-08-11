package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.CouponDto;
import com.ktsr.drinkIt.entity.Coupon;

import java.util.List;

public interface CouponService {
    Coupon createCoupon(CouponDto dto);

    Coupon updateCoupon(Long id, CouponDto dto);

    Coupon getCouponById(Long id);

    Coupon getCouponByCode(String code);

    List<Coupon> getAllCoupons();

    List<Coupon> getActiveCoupons();

    List<Coupon> getValidCoupons();

    Coupon activateCoupon(Long id);

    Coupon deactivateCoupon(Long id);

    boolean existsById(Long id);

    void deleteCoupon(Long id);
}
