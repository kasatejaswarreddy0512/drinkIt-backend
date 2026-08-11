package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.CouponDto;
import com.ktsr.drinkIt.entity.Coupon;
import com.ktsr.drinkIt.repository.CouponRepository;
import com.ktsr.drinkIt.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponServiceImpl implements CouponService {

    private final CouponRepository couponRepository;

    @Override
    public Coupon createCoupon(CouponDto dto) {
        if (couponRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new IllegalArgumentException("Coupon code already exists.");
        }

        Coupon coupon = Coupon.builder()
                .code(dto.getCode().trim().toUpperCase())
                .description(dto.getDescription())
                .discountType(dto.getDiscountType())
                .discountValue(dto.getDiscountValue())
                .minimumAmount(dto.getMinimumAmount())
                .startDate(dto.getStartDate())
                .endDate(dto.getEndDate())
                .active(dto.getActive())
                .build();

        return couponRepository.save(coupon);
    }

    @Override
    public Coupon updateCoupon(Long id, CouponDto dto) {
        Coupon coupon = getCouponById(id);

        if (!coupon.getCode().equalsIgnoreCase(dto.getCode())
                && couponRepository.existsByCodeIgnoreCase(dto.getCode())) {
            throw new IllegalArgumentException("Coupon code already exists.");
        }

        coupon.setCode(dto.getCode().trim().toUpperCase());
        coupon.setDescription(dto.getDescription());
        coupon.setDiscountType(dto.getDiscountType());
        coupon.setDiscountValue(dto.getDiscountValue());
        coupon.setMinimumAmount(dto.getMinimumAmount());
        coupon.setStartDate(dto.getStartDate());
        coupon.setEndDate(dto.getEndDate());
        coupon.setActive(dto.getActive());

        return couponRepository.save(coupon);

    }

    @Override
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id).orElse(null);
    }

    @Override
    public Coupon getCouponByCode(String code) {
        return couponRepository.findByCodeIgnoreCase(code).orElse(null);
    }

    @Override
    public List<Coupon> getAllCoupons() {
        return couponRepository.findAll();
    }

    @Override
    public List<Coupon> getActiveCoupons() {
        return couponRepository.findByActiveTrue();
    }

    @Override
    public List<Coupon> getValidCoupons() {
        LocalDate today = LocalDate.now();
        return couponRepository.findByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(today, today);
    }

    @Override
    public Coupon activateCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        coupon.setActive(true);

        return couponRepository.save(coupon);
    }

    @Override
    public Coupon deactivateCoupon(Long id) {
        Coupon coupon = getCouponById(id);
        coupon.setActive(false);
        return couponRepository.save(coupon);
    }

    @Override
    public boolean existsById(Long id) {
        return couponRepository.existsById(id);
    }

    @Override
    public void deleteCoupon(Long id) {
        couponRepository.deleteById(id);
    }
}

