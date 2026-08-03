package com.ktsr.drinkIt.repository;

import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.enums.Role;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @EntityGraph(attributePaths = "addresses")
    User findByEmail(String email);

    Optional<User> findByPhone(String phone);

    boolean existsByEmail(String email);

    boolean existsByPhone(String phone);

    List<User> findByRole(Role role);

    List<User> findByActiveTrue();

    List<User> findByVerifiedTrue();
}
