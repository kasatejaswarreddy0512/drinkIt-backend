package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.ProductVariantDto;
import com.ktsr.drinkIt.entity.ProductVariant;

import java.util.List;

public interface ProductVariantService {

    ProductVariant createVariant(ProductVariantDto productVariantDto);

    ProductVariant updateVariant(Long id, ProductVariantDto productVariantDto);

    ProductVariant getVariantById(Long id);

    List<ProductVariant> getAllVariants();

    List<ProductVariant> getActiveVariants();

    List<ProductVariant> getVariantsByProduct(Long productId);

    List<ProductVariant> getActiveVariantsByProduct(Long productId);

    ProductVariant getVariantBySku(String sku);

    ProductVariant getVariantByBarcode(String barcode);

    List<ProductVariant> getVariantsByVolume(String volume);

    List<ProductVariant> getAvailableVariants();

    List<ProductVariant> getLowStockVariants(Integer stock);

    ProductVariant activateVariant(Long id);

    ProductVariant deactivateVariant(Long id);

    void deleteVariant(Long id);

    boolean existsById(Long id);
}