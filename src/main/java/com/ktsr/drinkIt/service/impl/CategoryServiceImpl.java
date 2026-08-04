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

        if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        return categoryRepository.save(category);
    }

    @Override
    public Category updateCategory(Long id,Category category) {
        Category existing = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " does not exist"));

        if(categoryRepository.existsByNameIgnoreCase(category.getName())) {
            throw new IllegalArgumentException("Category with name " + category.getName() + " already exists");
        }
        existing.setName(category.getName());
        existing.setActive(category.getActive());
        existing.setDescription(category.getDescription());
        existing.setImage(category.getImage());
        return categoryRepository.save(existing);
    }

    @Override
    public Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " does not exist"));
    }

    @Override
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void deleteCategory(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category activateCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " does not exist"));
        category.setActive(true);
        return categoryRepository.save(category);
    }

    @Override
    public Category deactivateCategory(Long id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Category with id " + id + " does not exist"));
        category.setActive(false);
        return categoryRepository.save(category);
    }

    @Override
    public boolean existsCategory(Long id) {
        return categoryRepository.existsById(id);
    }
}
