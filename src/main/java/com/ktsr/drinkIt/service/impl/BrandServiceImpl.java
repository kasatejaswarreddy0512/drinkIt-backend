package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.entity.Brand;
import com.ktsr.drinkIt.repository.BrandRepository;
import com.ktsr.drinkIt.service.BrandService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BrandServiceImpl implements BrandService {

    private final BrandRepository brandRepository;

    @Override
    public Brand createBrand(Brand brand) {
        if(brandRepository.existsByNameIgnoreCase(brand.getName())) {
            throw new IllegalArgumentException("Brand with name " + brand.getName() + " already exists");
        }
        return brandRepository.save(brand);
    }

    @Override
    public Brand updateBrand(Long id, Brand brand) {
        Brand existing = brandRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Brand with id " + id + " does not exist"));
        existing.setName(brand.getName());
        existing.setDescription(brand.getDescription());
        existing.setLogo(brand.getLogo());
        existing.setActive(brand.getActive());
        return brandRepository.save(existing);
    }

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Brand getBrandById(Long id) {
        return brandRepository.findById(id).orElse(null);
    }


    @Override
    public List<Brand> getActiveBrands() {
        return brandRepository.findByActiveTrue();
    }

    @Override
    public void deleteBrand(Long id) {
        if (!brandRepository.existsById(id)) {
            throw new IllegalArgumentException("Brand with id " + id + " does not exist");
        }
        brandRepository.deleteById(id);
    }

    @Override
    public boolean existsBrand(Long id) {
        return brandRepository.existsById(id);
    }
}
