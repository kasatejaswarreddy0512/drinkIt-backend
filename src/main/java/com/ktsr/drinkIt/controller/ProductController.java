package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.ProductDto;
import com.ktsr.drinkIt.entity.Product;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController  {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProduct(@Valid @RequestBody ProductDto product) {
        try {
            Product createdProduct = productService.createProduct(product);
            return APIResponse.get(ErrorCode.SUCCESS, createdProduct, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getProductById(@PathVariable Long id) {
        try {
            boolean exists= productService.existsProduct(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_NOT_FOUND, "Product not found", HttpStatus.NOT_FOUND);
            }
            Product product = productService.getProductById(id);
            return APIResponse.get(ErrorCode.SUCCESS, product, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveProducts() {
        try {
            List<Product> products = productService.getActiveProducts();
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<ResponseWrapper> getProductByCategory(@PathVariable Long categoryId){
        try {
            List<Product> products = productService.getProductsByCategory(categoryId);
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/brand/{brandId}")
    public ResponseEntity<ResponseWrapper> getProductByBrand(@PathVariable Long brandId){
        try {
            List<Product> products = productService.getProductsByBrand(brandId);
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<ResponseWrapper> getProductBySearch(@RequestParam String search){
        try {
            List<Product> products = productService.searchProducts(search);
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}/brand/{brandId}")
    public  ResponseEntity<ResponseWrapper> getProductByCategoryAndBrand(@PathVariable Long categoryId, @PathVariable Long brandId){
        try {
            List<Product> products = productService.getProductsByCategoryAndBrand(categoryId, brandId);
            return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/rating/{rating}")
    public ResponseEntity<ResponseWrapper> getProductByRating(@PathVariable Double rating){
        try {
         List<Product> products = productService.getProductsByRating(rating);
         return APIResponse.get(ErrorCode.SUCCESS, products, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateProduct(@PathVariable Long id,@Valid @RequestBody ProductDto product) {
        try {
            boolean exists= productService.existsProduct(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_NOT_FOUND, "Product not found", HttpStatus.NOT_FOUND);
            }
            Product updatedProduct = productService.updateProduct(id, product);
            return APIResponse.get(ErrorCode.SUCCESS, updatedProduct, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<ResponseWrapper> activateProduct(@PathVariable Long id) {
        try {
            boolean exists= productService.existsProduct(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_NOT_FOUND, "Product not found", HttpStatus.NOT_FOUND);
            }
            Product activatedProduct = productService.activateProduct(id);
            return APIResponse.get(ErrorCode.SUCCESS, activatedProduct, HttpStatus.OK);
        }catch (Exception e){
            return  APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ResponseWrapper> deactivateProduct(@PathVariable Long id) {
        try {
            boolean exists= productService.existsProduct(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_NOT_FOUND, "Product not found", HttpStatus.NOT_FOUND);
            }
            Product deactivatedProduct = productService.deactivateProduct(id);
            return APIResponse.get(ErrorCode.SUCCESS, deactivatedProduct, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteProduct(@PathVariable Long id) {
        try {
            boolean exists= productService.existsProduct(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_NOT_FOUND, "Product not found", HttpStatus.NOT_FOUND);
            }
            productService.deleteProduct(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Product deleted successfully", HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}