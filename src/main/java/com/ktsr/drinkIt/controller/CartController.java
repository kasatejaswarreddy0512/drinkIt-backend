package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.entity.Cart;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/carts")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper> getCartByUser(@PathVariable Long userId) {
        try {
            Cart cart= cartService.getCartByUser(userId);
            return APIResponse.get(ErrorCode.SUCCESS,cart,HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
