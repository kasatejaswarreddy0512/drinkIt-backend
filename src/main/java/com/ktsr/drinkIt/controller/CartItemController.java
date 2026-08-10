package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.CartItemDto;
import com.ktsr.drinkIt.entity.CartItem;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart-items")
@RequiredArgsConstructor
public class CartItemController {

    private final CartItemService cartItemService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> addCartItem(@Valid @RequestBody CartItemDto cartItem){
        try {
            CartItem savedCartItem = cartItemService.addItem(cartItem);
            return APIResponse.get(ErrorCode.SUCCESS,savedCartItem, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateItem(@PathVariable Long id, @RequestBody CartItemDto cartItemDto){
        try {
            boolean exists=cartItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CART_ITEM_NOT_FOUND,"Cart Item Not Found", HttpStatus.NOT_FOUND);
            }
            CartItem cartItem=cartItemService.updateCartItem(id,cartItemDto);
            return APIResponse.get(ErrorCode.SUCCESS,cartItem,HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getCartItem(@PathVariable Long id){
        try {
            boolean exists=cartItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CART_ITEM_NOT_FOUND,"Cart Item Not Found", HttpStatus.NOT_FOUND);
            }
            CartItem cartItem=cartItemService.getCartItemById(id);
            return APIResponse.get(ErrorCode.SUCCESS,cartItem,HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cart/{cartId}")
    public ResponseEntity<ResponseWrapper> getCartItemByCartId(@PathVariable Long cartId){
        try {
            List<CartItem> cartItems = cartItemService.getCartItemsByCart(cartId);
            return APIResponse.get(ErrorCode.SUCCESS, cartItems, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteCartItem(@PathVariable Long id){
        try {
            boolean exists = cartItemService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CART_ITEM_NOT_FOUND,"Cart Item Not Found", HttpStatus.NOT_FOUND);
            }
            cartItemService.removeCartItem(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Cart Item Removed Successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cart/{cartId}")
    public ResponseEntity<ResponseWrapper> deleteCartItems(@PathVariable Long cartId){
        try {
            cartItemService.clearCart(cartId);
            return APIResponse.get(ErrorCode.SUCCESS,"Cart Items Removed Successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
