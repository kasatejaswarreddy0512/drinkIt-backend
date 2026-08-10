package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.CartItemDto;
import com.ktsr.drinkIt.entity.Cart;
import com.ktsr.drinkIt.entity.CartItem;
import com.ktsr.drinkIt.entity.ProductVariant;
import com.ktsr.drinkIt.repository.CartItemRepository;
import com.ktsr.drinkIt.repository.CartRepository;
import com.ktsr.drinkIt.repository.ProductVariantRepository;
import com.ktsr.drinkIt.service.CartItemService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CartItemServiceImpl implements CartItemService {

    private final CartItemRepository cartItemRepository;
    private final CartRepository cartRepository;
    private final ProductVariantRepository productVariantRepository;

    @Override
    @Transactional
    public CartItem addItem(CartItemDto dto) {

        if (dto.getQuantity() == null || dto.getQuantity() <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than zero");
        }

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + dto.getCartId()));

        ProductVariant variant = productVariantRepository
                .findById(dto.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found with id: " + dto.getProductVariantId()));

        if (!Boolean.TRUE.equals(variant.getActive())) {
            throw new IllegalArgumentException("Product variant is inactive");
        }

        if (variant.getStock() == null || variant.getStock() <= 0) {
            throw new IllegalArgumentException("Product variant is out of stock");
        }

        Optional<CartItem> existingItem =
                cartItemRepository.findByCartIdAndProductVariantId(dto.getCartId(), dto.getProductVariantId());

        CartItem cartItem;
        if (existingItem.isPresent()) {
            cartItem = existingItem.get();
            int newQuantity = cartItem.getQuantity() + dto.getQuantity();

            if (newQuantity > variant.getStock()) {
                throw new IllegalArgumentException("Insufficient stock. Available stock: " + variant.getStock());
            }

            cartItem.setQuantity(newQuantity);
            cartItem.setPrice(variant.getPrice());
            cartItem.setSubtotal(variant.getPrice().multiply(BigDecimal.valueOf(newQuantity)));

        } else {

            if (dto.getQuantity() > variant.getStock()) {
                throw new IllegalArgumentException("Insufficient stock. Available stock: " + variant.getStock());
            }

            BigDecimal subtotal = variant.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()));

            cartItem = CartItem.builder()
                    .cart(cart)
                    .productVariant(variant)
                    .quantity(dto.getQuantity())
                    .price(variant.getPrice())
                    .subtotal(subtotal)
                    .build();
        }
        CartItem savedItem = cartItemRepository.save(cartItem);
         updateCartTotal(cart.getId());

        return savedItem;
    }

    @Override
    public CartItem updateCartItem(Long id, CartItemDto dto) {

        CartItem item = getCartItemById(id);

        Cart cart = cartRepository.findById(dto.getCartId())
                .orElseThrow(() -> new EntityNotFoundException("Cart not found"));

        ProductVariant variant = productVariantRepository.findById(dto.getProductVariantId())
                .orElseThrow(() -> new EntityNotFoundException("Product Variant not found"));

        item.setCart(cart);
        item.setProductVariant(variant);
        item.setQuantity(dto.getQuantity());
        item.setPrice(variant.getPrice());
        item.setSubtotal(
                variant.getPrice().multiply(BigDecimal.valueOf(dto.getQuantity()))
        );

        CartItem updatedItem = cartItemRepository.save(item);

        updateCartTotal(cart.getId());

        return updatedItem;
    }

    @Override
    public CartItem getCartItemById(Long id) {
        return cartItemRepository.findById(id).orElse(null);
    }

    @Override
    public List<CartItem> getCartItemsByCart(Long cartId) {
        return cartItemRepository.findByCartId(cartId);
    }

    @Override
    public boolean existsById(Long id) {
        return cartItemRepository.existsById(id);
    }

    @Override
    public void removeCartItem(Long id) {
        CartItem item = getCartItemById(id);
        cartItemRepository.delete(item);
    }

    @Override
    public void clearCart(Long cartId) {
        cartItemRepository.deleteByCartId(cartId);
    }

    private void updateCartTotal(Long cartId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new EntityNotFoundException("Cart not found with id: " + cartId));

        List<CartItem> items = cartItemRepository.findByCartId(cartId);
        BigDecimal total = items.stream()
                .map(CartItem::getSubtotal)
                .filter(Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        cart.setTotalAmount(total);

        cartRepository.save(cart);
    }
}
