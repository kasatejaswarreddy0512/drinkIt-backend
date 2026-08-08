package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.ProductImageDto;
import com.ktsr.drinkIt.entity.ProductImage;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.ProductImageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-image")
@RequiredArgsConstructor
public class ProductImageController {

    private final ProductImageService productImageService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> addProductImage(@Valid @RequestBody ProductImageDto productImage){
        try {
            ProductImage saved=productImageService.createProductImage(productImage);
            return APIResponse.get(ErrorCode.SUCCESS,saved, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateProductImage(@Valid @RequestBody ProductImageDto productImage, @PathVariable Long id){
        try {
            boolean exists=productImageService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND,"Product Image not found", HttpStatus.NOT_FOUND);
            }
            ProductImage updated=productImageService.updateProductImage(id, productImage);
            return APIResponse.get(ErrorCode.SUCCESS,updated, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getProductImage(@PathVariable Long id){
        try {
            boolean exists=productImageService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND,"Product Image not found", HttpStatus.NOT_FOUND);
            }
            ProductImage productImage=productImageService.getProductImageById(id);
            return APIResponse.get(ErrorCode.SUCCESS,productImage, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getProductImageAll(){
        try {
            List<ProductImage> productImages=productImageService.getAllProductImages();
            return APIResponse.get(ErrorCode.SUCCESS,productImages, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveProductImage(){
        try {
            List<ProductImage> productImages=productImageService.getActiveProductImages();
            return APIResponse.get(ErrorCode.SUCCESS,productImages, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper> getProductImagesByProduct(@PathVariable Long productId){
        try {
            List<ProductImage> productImages=productImageService.getProductImagesByProduct(productId);
            return APIResponse.get(ErrorCode.SUCCESS,productImages, HttpStatus.OK);
        }
        catch (Exception e){
            return  APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/primary")
    public ResponseEntity<ResponseWrapper> getPrimaryImage(@RequestParam Long productId){
        try {
            ProductImage productImage=productImageService.getPrimaryImage(productId);
            return APIResponse.get(ErrorCode.SUCCESS,productImage, HttpStatus.OK);
        }
        catch (Exception e){
            return  APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/primary")
    public ResponseEntity<ResponseWrapper> setPrimaryImage(@RequestParam Long productId, @RequestParam Long imageId) {
        try {
            boolean exists = productImageService.existsById(imageId);
            if (!exists) {
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND, "Product Image not found", HttpStatus.NOT_FOUND);
            }
            ProductImage productImage = productImageService.setPrimaryImage(productId, imageId);
            return APIResponse.get(ErrorCode.SUCCESS, productImage, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<ResponseWrapper> setActiveImage(@PathVariable Long id) {
        try {
            boolean exists = productImageService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND, "Product Image not found", HttpStatus.NOT_FOUND);
            }
            ProductImage productImage=productImageService.activateProductImage(id);
            return APIResponse.get(ErrorCode.SUCCESS,productImage, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ResponseWrapper> setDeactivateImage(@PathVariable Long id) {
        try {
            boolean exists = productImageService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND, "Product Image not found", HttpStatus.NOT_FOUND);
            }
            ProductImage productImage=productImageService.deactivateProductImage(id);
            return APIResponse.get(ErrorCode.SUCCESS,productImage, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteProductImage(@PathVariable Long id){
        try {
            boolean exists = productImageService.existsById(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.PRODUCT_IMAGE_NOT_FOUND, "Product Image not found", HttpStatus.NOT_FOUND);
            }
            productImageService.deleteProductImage(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Product Image deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}