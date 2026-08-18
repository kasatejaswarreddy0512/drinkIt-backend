package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.SellerReviewDto;
import com.ktsr.drinkIt.entity.SellerReview;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.SellerReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/seller-reviews")
@RequiredArgsConstructor
public class SellerReviewController {

    private final SellerReviewService sellerReviewService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createReview(@Valid @RequestBody SellerReviewDto dto){
        try {
            SellerReview review= sellerReviewService.createReview(dto);
            return APIResponse.get(ErrorCode.SUCCESS,review,HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateReview(@PathVariable Long id, @Valid @RequestBody SellerReviewDto dto){
        try {
            boolean exists= sellerReviewService.exitsSellerReview(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_REVIEW_NOT_FOUND,"Seller review not found.",HttpStatus.NOT_FOUND);
            }
            SellerReview sellerReview= sellerReviewService.updateReview(id, dto);
            return APIResponse.get(ErrorCode.SUCCESS,sellerReview,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getReviewById(@PathVariable Long id){
        try {
            boolean exists= sellerReviewService.exitsSellerReview(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_REVIEW_NOT_FOUND,"Seller review not found.",HttpStatus.NOT_FOUND);
            }
            SellerReview sellerReview = sellerReviewService.getSellerReviewById(id);
            return APIResponse.get(ErrorCode.SUCCESS,sellerReview,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getReviews(){
        try {
            List<SellerReview> sellerReviews = sellerReviewService.getSellerReviews();
            return APIResponse.get(ErrorCode.SUCCESS,sellerReviews,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/seller")
    public ResponseEntity<ResponseWrapper> getSellerBySellerId(@RequestParam Long sellerId){
        try {
            List<SellerReview> review=sellerReviewService.getSellerReviewsBySellerId(sellerId);
            return APIResponse.get(ErrorCode.SUCCESS,review,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user")
    public ResponseEntity<ResponseWrapper> getUserBySellerId(@RequestParam Long userId){
        try {
            List<SellerReview> reviews= sellerReviewService.getSellerReviewsByUserId(userId);
            return APIResponse.get(ErrorCode.SUCCESS,reviews,HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteReview(@PathVariable Long id){
        try {
            boolean exists= sellerReviewService.exitsSellerReview(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_REVIEW_NOT_FOUND,"Seller review not found.",HttpStatus.NOT_FOUND);
            }
            sellerReviewService.deleteReview(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Seller Review Deleted Successfully..!",HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}