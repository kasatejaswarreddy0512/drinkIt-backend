package com.ktsr.drinkIt.service;


import com.ktsr.drinkIt.entity.Category;

import java.util.List;

public interface CategoryService {
    Category createCategory(Category category);
    Category updateCategory(Category category);
    Category getCategoryById(Long id);
    List<Category> getAllCategories();
    void deleteCategory(Category category);
}
