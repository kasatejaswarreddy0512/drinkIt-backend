package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.ProductVariantDto;
import com.ktsr.drinkIt.entity.ProductVariant;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.ProductVariantService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product-variants")
@RequiredArgsConstructor
public class ProductVariantController {

    private final ProductVariantService productVariantService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createProductVariant(@Valid @RequestBody ProductVariantDto productVariant){
        try {
            ProductVariant saved=productVariantService.createVariant(productVariant);
            return APIResponse.get(ErrorCode.SUCCESS,saved, HttpStatus.CREATED);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateProductVariant(@PathVariable Long id, @Valid @RequestBody ProductVariantDto productVariant){
        try {
            boolean exits=productVariantService.existsById(id);
            if(!exits){
                return APIResponse.get(ErrorCode.PRODUCT_VARIANT_NOT_FOUND,"Product Variant not found", HttpStatus.NOT_FOUND);
            }
            ProductVariant updated=productVariantService.updateVariant(id, productVariant);
            return APIResponse.get(ErrorCode.SUCCESS,updated, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllProductVariants(){
        try {
            List<ProductVariant> variants=productVariantService.getAllVariants();
            return APIResponse.get(ErrorCode.SUCCESS,variants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getProductVariant(@PathVariable Long id){
        try {
            boolean exists=productVariantService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_VARIANT_NOT_FOUND,"Product Variant not found", HttpStatus.NOT_FOUND);
            }
            ProductVariant variant=productVariantService.getVariantById(id);
            return APIResponse.get(ErrorCode.SUCCESS,variant, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

   @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveProductVariants(){
        try {
            List<ProductVariant> variants=productVariantService.getActiveVariants();
            return APIResponse.get(ErrorCode.SUCCESS,variants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

   @GetMapping("/product/{productId}")
    public ResponseEntity<ResponseWrapper> getProductVariantById(@PathVariable Long productId){
        try {
            List<ProductVariant> variants=productVariantService.getVariantsByProduct(productId);
            return APIResponse.get(ErrorCode.SUCCESS,variants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

   @GetMapping("/active/product/{productId}")
    public ResponseEntity<ResponseWrapper> getActiveProductVariantById(@PathVariable Long productId){
        try {
            List<ProductVariant> variants=productVariantService.getActiveVariantsByProduct(productId);
            return APIResponse.get(ErrorCode.SUCCESS,variants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
   }

   @GetMapping("/sku")
    public ResponseEntity<ResponseWrapper> getProductSku(@RequestParam String sku){
        try {
            ProductVariant variant=productVariantService.getVariantBySku(sku);
            return APIResponse.get(ErrorCode.SUCCESS,variant, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/barcode")
    public ResponseEntity<ResponseWrapper> getProductBarcode(@RequestParam String barcode){
        try {
            ProductVariant variant=productVariantService.getVariantByBarcode(barcode);
            return APIResponse.get(ErrorCode.SUCCESS,variant, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/volume")
    public ResponseEntity<ResponseWrapper> getProductVolume(@RequestParam String volume){
        try {
            List<ProductVariant> variants=productVariantService.getVariantsByVolume(volume);
            return APIResponse.get(ErrorCode.SUCCESS,variants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/variant")
    public ResponseEntity<ResponseWrapper> getProductVariant(){
        try {
            List<ProductVariant> productVariants=productVariantService. getAvailableVariants();
            return APIResponse.get(ErrorCode.SUCCESS,productVariants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/stock")
    public ResponseEntity<ResponseWrapper> getProductStock(@RequestParam Integer stock){
        try {
            List<ProductVariant> productVariants=productVariantService.getLowStockVariants(stock);
            return APIResponse.get(ErrorCode.SUCCESS,productVariants, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/activate/{id}")
    public ResponseEntity<ResponseWrapper> activateProductVariant(@PathVariable Long id){
        try {
            boolean exists=productVariantService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_VARIANT_NOT_FOUND,"Product Variant not found",HttpStatus.NOT_FOUND);
            }
            ProductVariant variant=productVariantService.activateVariant(id);
            return APIResponse.get(ErrorCode.SUCCESS,variant, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactivate/{id}")
    public ResponseEntity<ResponseWrapper> deactivateProductVariant(@PathVariable Long id){
        try {
            boolean exists=productVariantService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_VARIANT_NOT_FOUND,"Product Variant not found",HttpStatus.NOT_FOUND);
            }
            ProductVariant variant=productVariantService.deactivateVariant(id);
            return APIResponse.get(ErrorCode.SUCCESS,variant, HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteProductVariant(@PathVariable Long id){
        try {
            boolean exists=productVariantService.existsById(id);
            if(!exists){
                return APIResponse.get(ErrorCode.PRODUCT_VARIANT_NOT_FOUND,"Product Variant not found",HttpStatus.NOT_FOUND);
            }
            productVariantService.deleteVariant(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Product Variant deleted successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}