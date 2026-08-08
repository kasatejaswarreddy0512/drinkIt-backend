package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductVariantRepository extends JpaRepository<ProductVariant, Long> {

    List<ProductVariant> findByProductId(Long productId);

    List<ProductVariant> findByProductIdAndActiveTrue(Long productId);

    Optional<ProductVariant> findBySku(String sku);

    Optional<ProductVariant> findByBarcode(String barcode);

    boolean existsBySku(String sku);

    boolean existsByBarcode(String barcode);

    List<ProductVariant> findByStockGreaterThan(Integer stock);

    List<ProductVariant> findByStockLessThanEqual(Integer stock);

    List<ProductVariant> findByActiveTrue();

    List<ProductVariant> findByVolume(String volume);
}