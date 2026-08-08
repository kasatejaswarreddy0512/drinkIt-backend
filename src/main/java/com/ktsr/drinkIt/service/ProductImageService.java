package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.ProductImageDto;
import com.ktsr.drinkIt.entity.ProductImage;

import java.util.List;

public interface ProductImageService {

    ProductImage createProductImage(ProductImageDto productImageDto);

    ProductImage updateProductImage(Long id, ProductImageDto productImageDto);

    ProductImage getProductImageById(Long id);

    List<ProductImage> getAllProductImages();

    List<ProductImage> getActiveProductImages();

    List<ProductImage> getProductImagesByProduct(Long productId);

    ProductImage getPrimaryImage(Long productId);

    ProductImage setPrimaryImage(Long productId, Long imageId);

    ProductImage activateProductImage(Long id);

    ProductImage deactivateProductImage(Long id);

    boolean existsById(Long id);

    void deleteProductImage(Long id);
}
