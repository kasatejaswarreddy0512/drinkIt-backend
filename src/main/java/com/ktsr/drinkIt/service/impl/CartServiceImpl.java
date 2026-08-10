package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.entity.Cart;
import com.ktsr.drinkIt.repository.CartRepository;
import com.ktsr.drinkIt.service.CartService;
import com.ktsr.drinkIt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final UserService  userService;

    @Override
    public Cart getCartByUser(Long userId) {
        boolean existsUser= userService.existsUser(userId);
        if(!existsUser){
            throw new IllegalArgumentException("User not found");
        }
        return cartRepository.findByUserId(userId);
    }
}
