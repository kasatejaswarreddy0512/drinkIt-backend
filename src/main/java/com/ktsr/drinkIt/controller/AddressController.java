package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.AddressDto;
import com.ktsr.drinkIt.entity.Address;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.AddressService;
import com.ktsr.drinkIt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final UserService userService;

    @PostMapping("/{userId}")
    public ResponseEntity<ResponseWrapper> addAddress(@PathVariable Long userId,@Valid @RequestBody AddressDto address){
        try {
            boolean exists=userService.existsUser(userId);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            Address saved = addressService.addAddress(userId,address);
            return APIResponse.get(ErrorCode.SUCCESS,saved,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateAddress(@PathVariable Long id,@Valid @RequestBody Address address){
        try {
            boolean exists=addressService.existsAddress(id);
            if(!exists){
                return APIResponse.get(ErrorCode.ADDRESS_NOT_FOUND,"Address not found.", HttpStatus.NOT_FOUND);
            }
            Address saved = addressService.updateAddress(id,address);
            return APIResponse.get(ErrorCode.SUCCESS,saved,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<ResponseWrapper> getAddressByUser(@PathVariable Long userId) {
        try {
            boolean exists=userService.existsUser(userId);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            List<Address> addresses=addressService.getAddressesByUser(userId);
            return APIResponse.get(ErrorCode.SUCCESS,addresses,HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/user/{userId}/default")
    public ResponseEntity<ResponseWrapper> getDefaultAddress(@PathVariable Long userId) {
        try {
            boolean exists=userService.existsUser(userId);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            Address addresses=addressService.getDefaultAddress(userId);
            return APIResponse.get(ErrorCode.SUCCESS,addresses,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/user/{userId}/default/{addressId}")
    public ResponseEntity<ResponseWrapper> updateDefaultAddress(@PathVariable Long userId, @PathVariable Long addressId) {
        try {
            boolean exists=addressService.existsAddress(userId,addressId);
            if(!exists){
                return APIResponse.get(ErrorCode.ADDRESS_NOT_FOUND,"Address not found.", HttpStatus.NOT_FOUND);
            }
            Address address=addressService.setDefaultAddress(userId,addressId);
            return APIResponse.get(ErrorCode.SUCCESS,address,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteAddress(@PathVariable Long id){
        try {
            boolean exists=addressService.existsAddress(id);
            if(!exists){
                return APIResponse.get(ErrorCode.ADDRESS_NOT_FOUND,"Address not found.", HttpStatus.NOT_FOUND);
            }
            addressService.deleteAddress(id);
            return APIResponse.get(ErrorCode.SUCCESS, "Address successfully deleted.", HttpStatus.OK);
        } catch (Exception e) {
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


}
