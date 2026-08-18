package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.SellerReviewDto;
import com.ktsr.drinkIt.entity.SellerReview;

import java.util.List;

public interface SellerReviewService {
    SellerReview createReview(SellerReviewDto dto);
    SellerReview updateReview(Long id ,SellerReviewDto dto);
    SellerReview getSellerReviewById(Long id);
    List<SellerReview> getSellerReviews();
    List<SellerReview> getSellerReviewsBySellerId(Long id);
    List<SellerReview> getSellerReviewsByUserId(Long id);
    boolean exitsSellerReview(Long id);
    void deleteReview(Long id);
}
