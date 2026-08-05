package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.entity.Brand;

import java.util.List;

public interface BrandService {

    Brand createBrand(Brand brand);
    Brand updateBrand(Long id, Brand brand);
    List<Brand> getAllBrands();
    Brand getBrandById(Long id);
    List<Brand> getActiveBrands();
    void deleteBrand(Long id);
    boolean existsBrand(Long id);

}
