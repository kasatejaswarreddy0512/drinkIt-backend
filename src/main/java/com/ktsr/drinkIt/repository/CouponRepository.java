package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Optional<Coupon> findByCodeIgnoreCase(String code);

    boolean existsByCodeIgnoreCase(String code);

    List<Coupon> findByActiveTrue();

    List<Coupon> findByStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate currentDate,
            LocalDate currentDate2);

    List<Coupon> findByActiveTrueAndStartDateLessThanEqualAndEndDateGreaterThanEqual(
            LocalDate currentDate,
            LocalDate currentDate2);
}
