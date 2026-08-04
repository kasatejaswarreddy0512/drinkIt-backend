package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.entity.Category;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createCategory(@RequestBody Category category){
        try {
            Category createdCategory = categoryService.createCategory(category);
            return APIResponse.get(ErrorCode.SUCCESS, createdCategory ,HttpStatus.CREATED);
        }
        catch (Exception e){
           return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateCategory(@PathVariable Long id, @RequestBody Category category){
        try {
            boolean exists = categoryService.existsCategory(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CATEGORY_NOT_FOUND, "Category with id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
            Category updatedCategory = categoryService.updateCategory(id, category);
            return APIResponse.get(ErrorCode.SUCCESS, updatedCategory ,HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllCategories(){
        try {
            List<Category> allCategories = categoryService.getAllCategories();
            return APIResponse.get(ErrorCode.SUCCESS, allCategories, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getCategory(@PathVariable Long id){
        try {
            boolean exists = categoryService.existsCategory(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CATEGORY_NOT_FOUND, "Category with id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
            Category category = categoryService.getCategoryById(id);
            return APIResponse.get(ErrorCode.SUCCESS, category, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<ResponseWrapper> changeActiveCategory(@PathVariable Long id){
        try {
            boolean exists = categoryService.existsCategory(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CATEGORY_NOT_FOUND, "Category with id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
            Category updatedCategory = categoryService.activateCategory(id);
            return APIResponse.get(ErrorCode.SUCCESS, updatedCategory, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ResponseWrapper> changeDeactivateCategory(@PathVariable Long id){
        try {
            boolean exists = categoryService.existsCategory(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CATEGORY_NOT_FOUND, "Category with id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
            Category updatedCategory = categoryService.deactivateCategory(id);
            return APIResponse.get(ErrorCode.SUCCESS, updatedCategory, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteCategory(@PathVariable Long id){
        try {
            boolean exists = categoryService.existsCategory(id);
            if(!exists){
                return APIResponse.get(ErrorCode.CATEGORY_NOT_FOUND, "Category with id " + id + " does not exist", HttpStatus.NOT_FOUND);
            }
            categoryService.deleteCategory(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Category deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
