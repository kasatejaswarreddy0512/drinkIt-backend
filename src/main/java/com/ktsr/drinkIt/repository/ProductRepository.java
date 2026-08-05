package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Brand;
import com.ktsr.drinkIt.entity.Category;
import com.ktsr.drinkIt.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByActiveTrue();

    List<Product> findByCategory(Category category);

    List<Product> findByCategoryId(Long categoryId);

    List<Product> findByBrand(Brand brand);

    List<Product> findByBrandId(Long brandId);

    List<Product> findByCategoryIdAndBrandId(Long categoryId, Long brandId);

    List<Product> findByNameContainingIgnoreCase(String name);

    List<Product> findByCategoryIdAndActiveTrue(Long categoryId);

    List<Product> findByBrandIdAndActiveTrue(Long brandId);

    List<Product> findByRatingGreaterThanEqual(Double rating);

    boolean existsByNameIgnoreCase(String name);

}
