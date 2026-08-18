package com.ktsr.drinkIt.controller;


import com.ktsr.drinkIt.DTO.SellerDto;
import com.ktsr.drinkIt.entity.Seller;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.repository.SellerRepository;
import com.ktsr.drinkIt.service.SellerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sellers")
@RequiredArgsConstructor
public class SellerController {

    private final SellerService  sellerService;

    @PostMapping
    public ResponseEntity<ResponseWrapper> registerSeller(@Valid @RequestBody SellerDto dto){
        try {
            Seller savedSeller= sellerService.registerSeller(dto);
            return APIResponse.get(ErrorCode.SUCCESS, savedSeller, HttpStatus.CREATED);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateSeller(@PathVariable Long id, @Valid @RequestBody SellerDto dto){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND,"Seller Not Found", HttpStatus.NOT_FOUND);
            }
            Seller seller= sellerService.updateSeller(id,dto);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.CREATED);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getSellerById(@PathVariable Long id){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND, "Seller Not Found ", HttpStatus.NOT_FOUND);
            }
            Seller seller = sellerService.getSellerById(id);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper> getSellers(){
        try {
            List<Seller> sellers = sellerService.getAllSellers();
            return APIResponse.get(ErrorCode.SUCCESS, sellers, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/email")
    public ResponseEntity<ResponseWrapper> getSellerByEmail(@RequestParam String email){
        try {
            Seller seller= sellerService.getByEmail(email);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/phone")
    public ResponseEntity<ResponseWrapper> getSellerByPhone(@RequestParam String phone){
        try {
            Seller seller= sellerService.getByPhone(phone);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/license-number")
    public ResponseEntity<ResponseWrapper> getSellerByLicenseNumber(@RequestParam String licenseNumber){
        try {
            Seller seller= sellerService.getByLicenseNumber(licenseNumber);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/shop-name")
    public ResponseEntity<ResponseWrapper> getSellerByShopName(@RequestParam String shopName){
        try {
            Seller seller= sellerService.getByShopName(shopName);
            return APIResponse.get(ErrorCode.SUCCESS, seller, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/active")
    public ResponseEntity<ResponseWrapper> getActiveSeller(){
        try {
            List<Seller> sellers = sellerService.getActiveSellers();
            return APIResponse.get(ErrorCode.SUCCESS, sellers, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/verified")
    public ResponseEntity<ResponseWrapper> getVerifiedSeller(){
        try{
            List<Seller> sellers = sellerService.getVerifiedSellers();
            return APIResponse.get(ErrorCode.SUCCESS, sellers, HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/active/{id}")
    public ResponseEntity<ResponseWrapper> activateSeller(@PathVariable Long id){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND, "Seller Not Found", HttpStatus.NOT_FOUND);
            }
            sellerService.activateSeller(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Seller Activated Successfully", HttpStatus.OK);
        }
        catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/deactiavate/{id}")
    public ResponseEntity<ResponseWrapper> deactivateSeller(@PathVariable Long id){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND, "Seller Not Found", HttpStatus.NOT_FOUND);
            }
            sellerService.deactivateSeller(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Seller Deactivated Successfully", HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/verify/{id}")
    public ResponseEntity<ResponseWrapper> verifySeller(@PathVariable Long id){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND, "Seller Not Found", HttpStatus.NOT_FOUND);
            }
            sellerService.verifySeller(id);
            return APIResponse.get(ErrorCode.SUCCESS,"Seller Verified Successfully", HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteSeller(@PathVariable Long id){
        try {
            boolean exists= sellerService.existsSeller(id);
            if(!exists){
                return APIResponse.get(ErrorCode.SELLER_NOT_FOUND, "Seller Not Found", HttpStatus.NOT_FOUND);
            }
            sellerService.deleteSeller(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Seller Deleted Successfully", HttpStatus.OK);
        }catch (Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Service Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
