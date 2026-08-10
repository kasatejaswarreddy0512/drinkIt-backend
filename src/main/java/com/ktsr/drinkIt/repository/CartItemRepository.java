package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    List<CartItem> findByCartId(Long cartId);

    Optional<CartItem> findByCartIdAndProductVariantId(Long cartId, Long productVariantId);

    boolean existsByCartIdAndProductVariantId(Long cartId, Long productVariantId);

    void deleteByCartId(Long cartId);


}
