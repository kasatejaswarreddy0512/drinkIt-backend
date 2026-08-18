package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.SellerDto;
import com.ktsr.drinkIt.entity.Seller;

import java.util.List;

public interface SellerService {

    Seller registerSeller(SellerDto dto);
    Seller updateSeller(Long id,SellerDto dto);
    Seller getSellerById(Long id);
    List<Seller> getAllSellers();
    Seller getByEmail(String email);
    Seller getByPhone(String phone);
    Seller getByLicenseNumber(String licenseNumber);
    Seller getByShopName(String shopName);
    List<Seller> getActiveSellers();
    List<Seller> getVerifiedSellers();
    void activateSeller(Long id);
    void deactivateSeller(Long id);
    void verifySeller(Long id);
    boolean existsSeller(Long id);
    void deleteSeller(Long id);
}
