package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.SellerReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SellerReviewRepository extends JpaRepository<SellerReview, Long> {

    List<SellerReview> findBySellerId(Long sellerId);

    List<SellerReview> findByUserId(Long userId);

    Optional<SellerReview> findBySellerIdAndUserId(Long sellerId, Long userId);

    boolean existsBySellerIdAndUserId(Long sellerId, Long userId);

    long countBySellerId(Long sellerId);
}
