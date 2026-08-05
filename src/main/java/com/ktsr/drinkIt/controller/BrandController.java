package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.entity.Brand;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/brands")
@RequiredArgsConstructor
public class BrandController {

    private final BrandService brandService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> createBrand(@RequestBody Brand brand) {
        try {
            Brand createBrand = brandService.createBrand(brand);
            return APIResponse.get(ErrorCode.SUCCESS,createBrand, HttpStatus.CREATED);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateBrand(@PathVariable Long id, @RequestBody Brand brand) {
        try {
            boolean exists=brandService.existsBrand(id);
            if(!exists){
                return APIResponse.get(ErrorCode.BRAND_NOT_FOUND, "Brand with id " + id + " does not exist", HttpStatus.BAD_REQUEST);
            }
            Brand updateBrand = brandService.updateBrand(id, brand);
            return APIResponse.get(ErrorCode.SUCCESS, updateBrand, HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getAllBrands(){
        try {
            return APIResponse.get(ErrorCode.SUCCESS, brandService.getAllBrands(), HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getBrandById(@PathVariable Long id) {
        try {
            boolean exists=brandService.existsBrand(id);
            if(!exists){
                return APIResponse.get(ErrorCode.BRAND_NOT_FOUND, "Brand with id " + id + " does not exist", HttpStatus.BAD_REQUEST);
            }
            return APIResponse.get(ErrorCode.SUCCESS, brandService.getBrandById(id), HttpStatus.OK);
        }
        catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveBrands() {
        try {
            return APIResponse.get(ErrorCode.SUCCESS, brandService.getActiveBrands(), HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteBrand(@PathVariable Long id) {
        try {
            boolean exists = brandService.existsBrand(id);
            if (!exists) {
                return APIResponse.get(ErrorCode.BRAND_NOT_FOUND, "Brand with id " + id + " does not exist", HttpStatus.BAD_REQUEST);
            }
            brandService.deleteBrand(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Brand deleted successfully", HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR, "Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
