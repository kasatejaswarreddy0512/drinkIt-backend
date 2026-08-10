package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.CartItemDto;
import com.ktsr.drinkIt.entity.CartItem;

import java.util.List;

public interface CartItemService {

    CartItem addItem(CartItemDto cartItem);

    CartItem updateCartItem(Long id, CartItemDto dto);

    CartItem getCartItemById(Long id);

    List<CartItem> getCartItemsByCart(Long cartId);

    boolean existsById(Long id);

    void removeCartItem(Long id);

    void clearCart(Long cartId);

}
