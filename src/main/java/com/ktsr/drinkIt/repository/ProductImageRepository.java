package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Optional;
@RequestMapping
public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {

    List<ProductImage> findByProductId(Long productId);

    List<ProductImage> findByProductIdAndActiveTrue(Long productId);

    Optional<ProductImage> findByProductIdAndPrimaryImageTrue(Long productId);

    List<ProductImage> findByActiveTrue();

    boolean existsByProductIdAndPrimaryImageTrue(Long productId);
}