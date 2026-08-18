package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.SellerReviewDto;
import com.ktsr.drinkIt.entity.Seller;
import com.ktsr.drinkIt.entity.SellerReview;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.repository.SellerRepository;
import com.ktsr.drinkIt.repository.SellerReviewRepository;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.SellerReviewService;
import com.ktsr.drinkIt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerReviewImpl implements SellerReviewService {

    private final SellerReviewRepository sellerReviewRepository;
    private final UserRepository userRepository;
    private final SellerRepository sellerRepository;

    @Override
    @Transactional
    public SellerReview createReview(SellerReviewDto dto) {

        User user = userRepository.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));

        Seller seller = sellerRepository.findById(dto.getSellerId())
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        if (sellerReviewRepository.existsBySellerIdAndUserId(
                dto.getSellerId(),
                dto.getUserId())) {

            throw new IllegalArgumentException(
                    "You have already reviewed this seller.");
        }

        SellerReview sellerReview = SellerReview.builder()
                .user(user)
                .seller(seller)
                .rating(dto.getRating())
                .review(dto.getReview())
                .build();

        SellerReview savedReview = sellerReviewRepository.save(sellerReview);
        updateSellerRating(seller);
        return savedReview;
    }

    @Override
    public SellerReview updateReview(Long id, SellerReviewDto dto) {
        SellerReview review= getSellerReviewById(id);
        review.setRating(dto.getRating());
        review.setReview(dto.getReview());
        SellerReview savedReview = sellerReviewRepository.save(review);
        updateSellerRating(review.getSeller());
        return savedReview;
    }

    @Override
    public SellerReview getSellerReviewById(Long id) {
        return sellerReviewRepository.findById(id).orElse(null);
    }

    @Override
    public List<SellerReview> getSellerReviews() {
        return sellerReviewRepository.findAll();
    }

    @Override
    public List<SellerReview> getSellerReviewsBySellerId(Long id) {
        return sellerReviewRepository.findBySellerId(id);
    }

    @Override
    public List<SellerReview> getSellerReviewsByUserId(Long id) {
        return sellerReviewRepository.findByUserId(id);
    }

    @Override
    public boolean exitsSellerReview(Long id) {
        return sellerReviewRepository.existsById(id);
    }

    @Override
    public void deleteReview(Long id) {
        SellerReview sellerReview = getSellerReviewById(id);
        sellerReviewRepository.delete(sellerReview);
        updateSellerRating(sellerReview.getSeller());
    }

    private void updateSellerRating(Seller seller) {

        Double averageRating = sellerReviewRepository.findBySellerId(seller.getId())
                .stream()
                .mapToDouble(SellerReview::getRating)
                .average()
                .orElse(0.0);

        seller.setRating(averageRating);
        sellerRepository.save(seller);
    }
}
