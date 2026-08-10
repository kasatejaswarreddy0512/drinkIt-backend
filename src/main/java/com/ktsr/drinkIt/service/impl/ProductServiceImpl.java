package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.ProductDto;
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
    public Product createProduct(ProductDto productDto) {

        if (productRepository.existsByNameIgnoreCase(productDto.getName())) {
            throw new IllegalArgumentException(
                    "Product already exists with name: " + productDto.getName()
            );
        }

        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Category not found with id: " + productDto.getCategoryId()
                        ));

        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id: " + productDto.getBrandId()
                        ));

        Product product = new Product();

        product.setName(productDto.getName());
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setBrand(brand);
        product.setImage(productDto.getImage());
        product.setRating(productDto.getRating());
        product.setActive(productDto.getActive());

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
    public Product updateProduct(Long id, ProductDto productDto) {

        Product existing = getProductById(id);

        if (existing == null) {
            throw new EntityNotFoundException(
                    "Product not found with id: " + id
            );
        }

        // Check duplicate product name
        if (!existing.getName().equalsIgnoreCase(productDto.getName())
                && productRepository.existsByNameIgnoreCase(productDto.getName())) {

            throw new IllegalArgumentException(
                    "Product already exists with name: " + productDto.getName()
            );
        }

        // Find category
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Category not found with id: "
                                        + productDto.getCategoryId()
                        ));

        // Find brand
        Brand brand = brandRepository.findById(productDto.getBrandId())
                .orElseThrow(() ->
                        new EntityNotFoundException(
                                "Brand not found with id: "
                                        + productDto.getBrandId()
                        ));

        // Update product
        existing.setName(productDto.getName());
        existing.setDescription(productDto.getDescription());
        existing.setCategory(category);
        existing.setBrand(brand);
        existing.setImage(productDto.getImage());
        existing.setRating(productDto.getRating());
        existing.setActive(productDto.getActive());

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
