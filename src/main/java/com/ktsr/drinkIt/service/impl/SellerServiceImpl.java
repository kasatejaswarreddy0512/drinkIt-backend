package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.SellerDto;
import com.ktsr.drinkIt.entity.Seller;
import com.ktsr.drinkIt.repository.SellerRepository;
import com.ktsr.drinkIt.service.SellerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SellerServiceImpl implements SellerService {

    private final SellerRepository sellerRepository;

    @Override
    public Seller registerSeller(SellerDto dto) {
        if(sellerRepository.existsByEmail(dto.getEmail())){
            throw  new RuntimeException("email already exists");
        }
        if(sellerRepository.existsByPhone(dto.getPhone())){
            throw  new RuntimeException("phone already exists");
        }
        if(sellerRepository.existsByLicenseNumber(dto.getLicenseNumber())){
            throw  new RuntimeException("licenseNumber already exists");
        }

        Seller seller= Seller.builder()
                .name(dto.getName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .licenseNumber(dto.getLicenseNumber())
                .shopName(dto.getShopName())
                .address(dto.getAddress())
                .profileImage(dto.getProfileImage())
                .gstNumber(dto.getGstNumber())
                .panNumber(dto.getPanNumber())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .country(dto.getCountry())
                .active(dto.getActive()!=null ? dto.getActive() : true)
                .verified(dto.getVerified()!=null ? dto.getVerified() : false)
                .build();
        return sellerRepository.save(seller);
    }

    @Override
    @Transactional
    public Seller updateSeller(Long id, SellerDto dto) {

        Seller seller = getSellerById(id);

        Seller emailSeller = sellerRepository.findByEmail(dto.getEmail());
        if (emailSeller != null && !emailSeller.getId().equals(id)) {
            throw new IllegalArgumentException("Email already exists.");
        }

        Seller phoneSeller = sellerRepository.findByPhone(dto.getPhone());
        if (phoneSeller != null && !phoneSeller.getId().equals(id)) {
            throw new IllegalArgumentException("Phone number already exists.");
        }

        Seller licenseSeller = sellerRepository.findByLicenseNumber(dto.getLicenseNumber());
        if (licenseSeller != null && !licenseSeller.getId().equals(id)) {
            throw new IllegalArgumentException("License number already exists.");
        }

        Seller shopSeller = sellerRepository.findByShopName(dto.getShopName());
        if (shopSeller != null && !shopSeller.getId().equals(id)) {
            throw new IllegalArgumentException("Shop name already exists.");
        }

        seller.setName(dto.getName());
        seller.setEmail(dto.getEmail());
        seller.setPhone(dto.getPhone());
        seller.setLicenseNumber(dto.getLicenseNumber());
        seller.setShopName(dto.getShopName());
        seller.setAddress(dto.getAddress());
        seller.setProfileImage(dto.getProfileImage());
        seller.setGstNumber(dto.getGstNumber());
        seller.setPanNumber(dto.getPanNumber());
        seller.setCity(dto.getCity());
        seller.setState(dto.getState());
        seller.setPincode(dto.getPincode());
        seller.setCountry(dto.getCountry());
        seller.setActive(dto.getActive());
        seller.setVerified(dto.getVerified());

        return sellerRepository.save(seller);
    }

    @Override
    public Seller getSellerById(Long id) {
        return sellerRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Seller not found"));
    }

    @Override
    public List<Seller> getAllSellers() {
        return sellerRepository.findAll();
    }

    @Override
    public Seller getByEmail(String email) {
        return sellerRepository.findByEmail(email);
    }

    @Override
    public Seller getByPhone(String phone) {
        return sellerRepository.findByPhone(phone);
    }

    @Override
    public Seller getByLicenseNumber(String licenseNumber) {
        return sellerRepository.findByLicenseNumber(licenseNumber);
    }

    @Override
    public Seller getByShopName(String shopName) {
        return sellerRepository.findByShopName(shopName);
    }

    @Override
    public List<Seller> getActiveSellers() {
        return sellerRepository.findByActiveTrue();
    }

    @Override
    public List<Seller> getVerifiedSellers() {
        return sellerRepository.findByVerifiedTrue();
    }

    @Override
    public void activateSeller(Long id) {
        Seller seller = getSellerById(id);
        seller.setActive(true);
        sellerRepository.save(seller);
    }

    @Override
    public void deactivateSeller(Long id) {
        Seller seller = getSellerById(id);
        seller.setActive(false);
        sellerRepository.save(seller);
    }

    @Override
    public void verifySeller(Long id) {
        Seller seller = getSellerById(id);
        seller.setVerified(true);
        sellerRepository.save(seller);
    }

    @Override
    public boolean existsSeller(Long id) {
        return sellerRepository.existsById(id);
    }

    @Override
    public void deleteSeller(Long id) {
    Seller seller = getSellerById(id);
    sellerRepository.delete(seller);
    }
}
