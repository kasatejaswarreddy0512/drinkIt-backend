package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.entity.Cart;

public interface CartService {
    Cart getCartByUser(Long userId);
}
