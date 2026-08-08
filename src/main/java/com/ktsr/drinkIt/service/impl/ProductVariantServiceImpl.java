package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.ProductVariantDto;
import com.ktsr.drinkIt.entity.Product;
import com.ktsr.drinkIt.entity.ProductVariant;
import com.ktsr.drinkIt.repository.ProductRepository;
import com.ktsr.drinkIt.repository.ProductVariantRepository;
import com.ktsr.drinkIt.service.ProductVariantService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductVariantServiceImpl implements ProductVariantService {

    private final ProductVariantRepository productVariantRepository;
    private final ProductRepository productRepository;

    @Override
    public ProductVariant createVariant(ProductVariantDto dto) {

        if (productVariantRepository.existsBySku(dto.getSku())) {
            throw new IllegalArgumentException("SKU already exists.");
        }

        if (dto.getBarcode() != null &&
                productVariantRepository.existsByBarcode(dto.getBarcode())) {
            throw new IllegalArgumentException("Barcode already exists.");
        }

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        ProductVariant variant = ProductVariant.builder()
                .product(product)
                .volume(dto.getVolume())
                .price(dto.getPrice())
                .stock(dto.getStock())
                .discount(dto.getDiscount())
                .sku(dto.getSku())
                .barcode(dto.getBarcode())
                .active(dto.getActive())
                .build();

        return productVariantRepository.save(variant);
    }

    @Override
    public ProductVariant updateVariant(Long id, ProductVariantDto dto) {

        ProductVariant existing = getVariantById(id);

        if (!existing.getSku().equals(dto.getSku())
                && productVariantRepository.existsBySku(dto.getSku())) {
            throw new IllegalArgumentException("SKU already exists.");
        }

        if (dto.getBarcode() != null
                && !dto.getBarcode().equals(existing.getBarcode())
                && productVariantRepository.existsByBarcode(dto.getBarcode())) {
            throw new IllegalArgumentException("Barcode already exists.");
        }

        Product product = productRepository.findById(dto.getProductId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        existing.setProduct(product);
        existing.setVolume(dto.getVolume());
        existing.setPrice(dto.getPrice());
        existing.setStock(dto.getStock());
        existing.setDiscount(dto.getDiscount());
        existing.setSku(dto.getSku());
        existing.setBarcode(dto.getBarcode());
        existing.setActive(dto.getActive());

        return productVariantRepository.save(existing);
    }

    @Override
    public ProductVariant getVariantById(Long id) {
        return productVariantRepository.findById(id).orElse(null);
    }

    @Override
    public List<ProductVariant> getAllVariants() {
        return productVariantRepository.findAll();
    }

    @Override
    public List<ProductVariant> getActiveVariants() {
        return productVariantRepository.findByActiveTrue();
    }

    @Override
    public List<ProductVariant> getVariantsByProduct(Long productId) {
        return productVariantRepository.findByProductId(productId);
    }

    @Override
    public List<ProductVariant> getActiveVariantsByProduct(Long productId) {
        return productVariantRepository.findByProductIdAndActiveTrue(productId);
    }

    @Override
    public ProductVariant getVariantBySku(String sku) {
        return productVariantRepository.findBySku(sku).orElse(null);
    }

    @Override
    public ProductVariant getVariantByBarcode(String barcode) {
        return productVariantRepository.findByBarcode(barcode).orElse(null);
    }

    @Override
    public List<ProductVariant> getVariantsByVolume(String volume) {
        return productVariantRepository.findByVolume(volume);
    }

    @Override
    public List<ProductVariant> getAvailableVariants() {
        return productVariantRepository.findByStockGreaterThan(0);
    }

    @Override
    public List<ProductVariant> getLowStockVariants(Integer stock) {
        return productVariantRepository.findByStockLessThanEqual(stock);
    }

    @Override
    public ProductVariant activateVariant(Long id) {
        ProductVariant variant = getVariantById(id);
        variant.setActive(true);

        return productVariantRepository.save(variant);
    }

    @Override
    public ProductVariant deactivateVariant(Long id) {
        ProductVariant variant = getVariantById(id);
        variant.setActive(false);

        return productVariantRepository.save(variant);
    }

    @Override
    public void deleteVariant(Long id) {
        ProductVariant variant = getVariantById(id);
        productVariantRepository.delete(variant);
    }

    @Override
    public boolean existsById(Long id) {
        return productVariantRepository.existsById(id);
    }
}
