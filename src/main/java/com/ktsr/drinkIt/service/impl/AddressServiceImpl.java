package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.DTO.AddressDto;
import com.ktsr.drinkIt.entity.Address;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.repository.AddressRepository;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.AddressService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;

    @Override
    public Address addAddress(Long userId, AddressDto addressDto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + userId)
                );

        if (Boolean.TRUE.equals(addressDto.getDefaultAddress())) {

            addressRepository.findByUserIdAndDefaultAddressTrue(userId)
                    .ifPresent(existingDefault -> {
                        existingDefault.setDefaultAddress(false);
                        addressRepository.save(existingDefault);
                    });
        }

        Address address = Address.builder()
                .user(user)
                .houseNo(addressDto.getHouseNo())
                .street(addressDto.getStreet())
                .area(addressDto.getArea())
                .city(addressDto.getCity())
                .state(addressDto.getState())
                .pincode(addressDto.getPincode())
                .latitude(addressDto.getLatitude())
                .longitude(addressDto.getLongitude())
                .defaultAddress(addressDto.getDefaultAddress())
                .build();

        return addressRepository.save(address);
    }

    @Override
    public Address updateAddress(Long id, Address address) {

        Address existing = addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Address not found"));


        existing.setHouseNo(address.getHouseNo());
        existing.setStreet(address.getStreet());
        existing.setArea(address.getArea());
        existing.setCity(address.getCity());
        existing.setState(address.getState());
        existing.setPincode(address.getPincode());
        existing.setLatitude(address.getLatitude());
        existing.setLongitude(address.getLongitude());
        existing.setDefaultAddress(address.getDefaultAddress());

        return addressRepository.save(existing);
    }

    @Override
    public Address getAddressById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found"));
    }

    @Override
    public List<Address> getAddressesByUser(Long userId) {
        return addressRepository.findByUserId(userId);
    }


    @Override
    public Address getDefaultAddress(Long userId) {
        return addressRepository.findByUserIdAndDefaultAddressTrue(userId)
                .orElseThrow(() -> new EntityNotFoundException("Default address not found"));
    }

    @Override
    public Address setDefaultAddress(Long userId, Long addressId) {
        addressRepository.findByUserIdAndDefaultAddressTrue(userId)
                .ifPresent(a -> {
                    a.setDefaultAddress(false);
                    addressRepository.save(a);
                });

        Address address = getAddressById(addressId);
        address.setDefaultAddress(true);

        return addressRepository.save(address);
    }

    @Override
    public void deleteAddress(Long id) {
        addressRepository.delete(getAddressById(id));
    }

    @Override
    public boolean existsAddress(Long userId, Long addressId) {
        return addressRepository.existsByIdAndUserId(userId, addressId);
    }

    @Override
    public boolean existsAddress(Long id) {
        return addressRepository.existsById(id);
    }
}
