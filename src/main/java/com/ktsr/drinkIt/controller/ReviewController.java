package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.ReviewDto;
import com.ktsr.drinkIt.entity.Review;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createReview(@Valid @RequestBody ReviewDto dto){
        try {
            Review review= reviewService.createReview(dto);
            return APIResponse.get(ErrorCode.SUCCESS, review, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getReviewById(@PathVariable Long id){
        try {
            boolean exists= reviewService.existsReview(id);
            if(!exists){
                return  APIResponse.get(ErrorCode.REVIEW_NOT_FOUND, "Review not found.", HttpStatus.NOT_FOUND);
            }
            Review review= reviewService.getReviewById(id);
            return APIResponse.get(ErrorCode.SUCCESS, review, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getReviews(){
        try {
            List<Review> reviews= reviewService.getAllReviews();
            return APIResponse.get(ErrorCode.SUCCESS, reviews, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product")
    public ResponseEntity<ResponseWrapper> getReviewsByProductId(@RequestParam Long productId){
        try {
            List<Review> reviews= reviewService.getReviewsByProduct(productId);
            return APIResponse.get(ErrorCode.SUCCESS, reviews, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper> getReviewsByUserId(@RequestParam Long userId){
        try {
            List<Review> reviews= reviewService.getReviewsByUser(userId);
            return APIResponse.get(ErrorCode.SUCCESS, reviews, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateReview(@PathVariable Long id,@Valid @RequestBody ReviewDto dto){
        try {
            boolean exists= reviewService.existsReview(id);
            if(!exists){
                return  APIResponse.get(ErrorCode.REFUND_NOT_FOUND, "Review not found.", HttpStatus.NOT_FOUND);
            }
            Review review= reviewService.updateReview(id, dto);
            return APIResponse.get(ErrorCode.SUCCESS, review, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteReview(@PathVariable Long id){
        try {
            boolean exists= reviewService.existsReview(id);
            if(!exists){
                return  APIResponse.get(ErrorCode.REFUND_NOT_FOUND, "Review not found.", HttpStatus.NOT_FOUND);
            }
            reviewService.deleteReview(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Review deleted successfully.", HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
