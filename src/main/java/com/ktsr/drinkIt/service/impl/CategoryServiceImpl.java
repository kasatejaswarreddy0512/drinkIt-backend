package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.entity.Category;
import com.ktsr.drinkIt.repository.CategoryRepository;
import com.ktsr.drinkIt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public Category createCategory(Category category) {
        return null;
    }

    @Override
    public Category updateCategory(Category category) {
        return null;
    }

    @Override
    public Category getCategoryById(Long id) {
        return null;
    }

    @Override
    public List<Category> getAllCategories() {
        return List.of();
    }

    @Override
    public void deleteCategory(Category category) {

    }
}
