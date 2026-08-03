package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.entity.User;

import java.util.List;

public interface UserService {

    User getUserFromJwtToken(String token) throws Exception;
    User getCurrentUser() throws Exception;
    User getUserByEmail(String email) throws Exception;
    User getUserById(Long id);
    List<User> getAllUsers();
    User updateUser(Long id, User user);

    void deleteUser(Long id);

    void activateUser(Long id);

    void deactivateUser(Long id);

    void verifyUser(Long id);

    boolean existsUser(Long id);
}
