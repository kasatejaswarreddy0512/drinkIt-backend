package com.ktsr.drinkIt.service;


import com.ktsr.drinkIt.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory( Long Id,Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Long id);

    Category activateCategory(Long id);

    Category deactivateCategory(Long id);

    boolean existsCategory(Long id);
}
