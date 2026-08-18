package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.ReviewDto;
import com.ktsr.drinkIt.entity.Product;
import com.ktsr.drinkIt.entity.Review;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.repository.ProductRepository;
import com.ktsr.drinkIt.repository.ReviewRepository;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    @Override
    public Review createReview(ReviewDto dto) {
        User user= userRepository.findById(dto.getUserId())
                .orElseThrow(()-> new RuntimeException("User not found"));

        Product product= productRepository.findById(dto.getProductId())
                .orElseThrow(()-> new RuntimeException("Product not found"));

        if(reviewRepository.existsByUserIdAndProductId(dto.getUserId(), dto.getProductId())) {
            throw new RuntimeException("You have already reviewed this product.");
        }

        Review review = Review.builder()
                .user(user)
                .product(product)
                .rating(dto.getRating())
                .review(dto.getReview())
                .build();
        Review savedReview = reviewRepository.save(review);

        updateProductRating(product);

        return savedReview;
    }

    @Override
    public Review updateReview(Long id, ReviewDto dto) {
        Review review= getReviewById(id);
        review.setRating(dto.getRating());
        review.setReview(dto.getReview());
        Review savedReview = reviewRepository.save(review);
        updateProductRating(review.getProduct());
        return  savedReview;
    }

    @Override
    public Review getReviewById(Long id) {
        return reviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    @Override
    public List<Review> getReviewsByProduct(Long productId) {
        return reviewRepository.findByProductId(productId);
    }

    @Override
    public List<Review> getReviewsByUser(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    @Override
    public boolean existsReview(Long id) {
        return reviewRepository.existsById(id);
    }

    @Override
    public void deleteReview(Long id) {
        Review review= getReviewById(id);
        Product product= review.getProduct();
        reviewRepository.delete(review);
        updateProductRating(product);
    }

    private void updateProductRating(Product product) {
        Double rating= reviewRepository.findByProductId(product.getId())
                .stream()
                .mapToDouble(Review::getRating)
                .average()
                .orElse(0.0);
        product.setRating(rating);
        productRepository.save(product);
    }

}
