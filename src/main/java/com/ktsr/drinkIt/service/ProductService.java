package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.entity.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(Product product);
    List<Product> getAllProducts();
    Product getProductById(Long id);
    List<Product> getActiveProducts();
    List<Product> getProductsByCategory(Long categoryId);
    List<Product> getProductsByBrand(Long brandId);
    List<Product> searchProducts(String name);
    Product updateProduct(Long id,Product product);
    List<Product> getProductsByCategoryAndBrand(Long categoryId, Long brandId);
    List<Product> getProductsByRating(Double rating);
    Product activateProduct(Long id);
    Product deactivateProduct(Long id);
    void deleteProduct(Long id);
    boolean existsProduct(Long id);

}
