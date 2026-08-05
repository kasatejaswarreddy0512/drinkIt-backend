package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.entity.Brand;
import com.ktsr.drinkIt.entity.Category;
import com.ktsr.drinkIt.entity.Product;
import com.ktsr.drinkIt.repository.BrandRepository;
import com.ktsr.drinkIt.repository.CategoryRepository;
import com.ktsr.drinkIt.repository.ProductRepository;
import com.ktsr.drinkIt.service.ProductService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final BrandRepository brandRepository;

    @Override
    public Product createProduct(Product product) {
        if (productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new IllegalArgumentException("Product already exists with name: " + product.getName());
        }

        Long categoryId = product.getCategory().getId();
        Long brandId = product.getBrand().getId();

        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(brandId)
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        product.setCategory(category);
        product.setBrand(brand);

        return productRepository.save(product);
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    @Override
    public List<Product> getActiveProducts() {
        return productRepository.findByActiveTrue();
    }

    @Override
    public List<Product> getProductsByCategory(Long categoryId) {
        return productRepository.findByCategoryId(categoryId);
    }

    @Override
    public List<Product> getProductsByBrand(Long brandId) {
        return productRepository.findByBrandId(brandId);
    }

    @Override
    public List<Product> searchProducts(String name) {
        return productRepository.findByNameContainingIgnoreCase(name);
    }

    @Override
    public Product updateProduct(Long id, Product product) {
        Product existing = getProductById(id);

        if (!existing.getName().equalsIgnoreCase(product.getName())
                && productRepository.existsByNameIgnoreCase(product.getName())) {
            throw new IllegalArgumentException("Product already exists with name: " + product.getName());
        }

        Category category = categoryRepository.findById(product.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        Brand brand = brandRepository.findById(product.getBrand().getId())
                .orElseThrow(() -> new EntityNotFoundException("Brand not found"));

        existing.setName(product.getName());
        existing.setDescription(product.getDescription());
        existing.setCategory(category);
        existing.setBrand(brand);
        existing.setImage(product.getImage());
        existing.setRating(product.getRating());
        existing.setActive(product.getActive());

        return productRepository.save(existing);
    }

    @Override
    public List<Product> getProductsByCategoryAndBrand(Long categoryId, Long brandId) {
        return productRepository.findByCategoryIdAndBrandId(categoryId, brandId);
    }

    @Override
    public List<Product> getProductsByRating(Double rating) {
        return productRepository.findByRatingGreaterThanEqual(rating);
    }

    @Override
    public Product activateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(true);
        return productRepository.save(product);
    }

    @Override
    public Product deactivateProduct(Long id) {
        Product product = getProductById(id);
        product.setActive(false);
        return productRepository.save(product);
    }

    @Override
    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    @Override
    public boolean existsProduct(Long id) {
        return productRepository.existsById(id);
    }
}
