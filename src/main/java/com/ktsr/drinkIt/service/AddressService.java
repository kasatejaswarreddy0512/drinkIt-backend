package com.ktsr.drinkIt.service;


import com.ktsr.drinkIt.DTO.AddressDto;
import com.ktsr.drinkIt.entity.Address;

import java.util.List;

public interface AddressService {

    Address addAddress(Long userId, AddressDto address);

    Address updateAddress(Long id,Address address);

    Address getAddressById(Long id);

    List<Address> getAddressesByUser(Long userId);

    Address getDefaultAddress(Long userId);

    Address setDefaultAddress(Long userId, Long addressId);

    void deleteAddress(Long id);

    boolean existsAddress(Long userId, Long addressId);

    boolean existsAddress(Long id);
}
