package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Seller;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SellerRepository extends JpaRepository<Seller,Long> {

    Seller findByEmail(String email);
    Seller findByPhone(String phone);
    Seller findByLicenseNumber(String licenseNumber);
    Seller findByShopName(String shopName);
    List<Seller> findByActiveTrue();
    List<Seller> findByVerifiedTrue();
    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByLicenseNumber(String licenseNumber);
    boolean existsByShopName(String shopName);

}
