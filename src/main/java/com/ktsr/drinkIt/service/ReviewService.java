package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.ReviewDto;
import com.ktsr.drinkIt.entity.Review;

import java.util.List;

public interface ReviewService {

    Review createReview(ReviewDto review);
    Review updateReview(Long id, ReviewDto review);

    Review getReviewById(Long id);
    List<Review> getAllReviews();
    List<Review> getReviewsByProduct(Long productId);
    List<Review> getReviewsByUser(Long userId);
    boolean existsReview(Long id);
    void deleteReview(Long id);
}
