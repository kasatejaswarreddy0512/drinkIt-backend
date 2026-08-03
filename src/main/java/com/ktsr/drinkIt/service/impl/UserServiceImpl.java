package com.ktsr.drinkIt.service.impl;

import com.ktsr.drinkIt.config.JwtProvider;
import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.mapper.UserMapper;
import com.ktsr.drinkIt.repository.UserRepository;
import com.ktsr.drinkIt.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User getUserFromJwtToken(String token) throws Exception {
        String email= jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User getCurrentUser() throws Exception {
        String email= SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found");
        }
        return user;
    }

    @Override
    public User getUserByEmail(String email) throws Exception {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new Exception("User not found email");
        }
        return user;
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User updateUser(Long id, User user) {
        User existing = getUserById(id);

        existing.setFullName(user.getFullName());
        existing.setEmail(user.getEmail());
        existing.setPassword(passwordEncoder.encode(user.getPassword()));
        existing.setPhone(user.getPhone());
        existing.setGender(user.getGender());
        existing.setDateOfBirth(user.getDateOfBirth());
        existing.setRole(user.getRole());

        return userRepository.save(existing);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public void activateUser(Long id) {
        User user = getUserById(id);
        user.setActive(true);
        userRepository.save(user);

    }

    @Override
    public void deactivateUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public void verifyUser(Long id) {
        User user = getUserById(id);
        user.setActive(false);
        userRepository.save(user);
    }

    @Override
    public boolean existsUser(Long id) {
        return userRepository.existsById(id);
    }
}
