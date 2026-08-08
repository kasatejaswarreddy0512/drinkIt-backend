package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.ProductImageDto;
import com.ktsr.drinkIt.entity.Product;
import com.ktsr.drinkIt.entity.ProductImage;
import com.ktsr.drinkIt.repository.ProductImageRepository;
import com.ktsr.drinkIt.repository.ProductRepository;
import com.ktsr.drinkIt.service.ProductImageService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductImageServiceImpl implements ProductImageService {

    private final ProductImageRepository productImageRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductImage createProductImage(ProductImageDto productImageDto) {
        Product product = productRepository.findById(productImageDto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (Boolean.TRUE.equals(productImageDto.getPrimaryImage())) {

            productImageRepository.findByProductIdAndPrimaryImageTrue(productImageDto.getProductId())
                    .ifPresent(image -> {
                        image.setPrimaryImage(false);
                        productImageRepository.save(image);
                    });
        }

        ProductImage image = ProductImage.builder()
                .product(product)
                .imageUrl(productImageDto.getImageUrl())
                .primaryImage(productImageDto.getPrimaryImage())
                .active(productImageDto.getActive())
                .build();

        return productImageRepository.save(image);
    }

    @Override
    public ProductImage updateProductImage(Long id, ProductImageDto dto) {

        ProductImage image = getProductImageById(id);

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        if (Boolean.TRUE.equals(dto.getPrimaryImage())) {

            productImageRepository.findByProductIdAndPrimaryImageTrue(dto.getProductId())
                    .ifPresent(existing -> {
                        if (!existing.getId().equals(id)) {
                            existing.setPrimaryImage(false);
                            productImageRepository.save(existing);
                        }
                    });
        }

        image.setProduct(product);
        image.setImageUrl(dto.getImageUrl());
        image.setPrimaryImage(dto.getPrimaryImage());
        image.setActive(dto.getActive());

        return productImageRepository.save(image);
    }

    @Override
    public ProductImage getProductImageById(Long id) {
        return productImageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Product image not found"));
    }

    @Override
    public List<ProductImage> getAllProductImages() {
        return productImageRepository.findAll();
    }

    @Override
    public List<ProductImage> getActiveProductImages() {
        return productImageRepository.findByActiveTrue();
    }

    @Override
    public List<ProductImage> getProductImagesByProduct(Long productId) {
        return productImageRepository.findByProductId(productId);
    }

    @Override
    public ProductImage getPrimaryImage(Long productId) {
        return productImageRepository.findByProductIdAndPrimaryImageTrue(productId)
                .orElseThrow(() -> new EntityNotFoundException("Primary image not found for product id: " + productId));
    }

    @Override
    public ProductImage setPrimaryImage(Long productId, Long imageId) {
        productImageRepository.findByProductIdAndPrimaryImageTrue(productId)
                .ifPresent(image -> {
                    image.setPrimaryImage(false);
                    productImageRepository.save(image);
                });
        ProductImage image = getProductImageById(imageId);
        image.setPrimaryImage(true);
        return productImageRepository.save(image);
    }

    @Override
    public ProductImage activateProductImage(Long id) {
        ProductImage productImage = getProductImageById(id);
        productImage.setActive(true);
        return productImageRepository.save(productImage);
    }

    @Override
    public ProductImage deactivateProductImage(Long id) {
        ProductImage productImage = getProductImageById(id);
        productImage.setActive(false);
        return productImageRepository.save(productImage);
    }

    @Override
    public boolean existsById(Long id) {
        return productImageRepository.existsById(id);
    }

    @Override
    public void deleteProductImage(Long id) {
        ProductImage image = getProductImageById(id);
        productImageRepository.delete(image);
    }
}
