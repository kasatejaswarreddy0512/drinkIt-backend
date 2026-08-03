package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
    List<Address> findByUserId(Long userId);

    Optional<Address> findByUserIdAndDefaultAddressTrue(Long userId);

    List<Address> findByCity(String city);

    List<Address> findByPincode(String pincode);

    boolean existsByIdAndUserId(Long id, Long userId);
}
