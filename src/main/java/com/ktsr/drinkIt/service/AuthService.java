package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.UserDto;
import com.ktsr.drinkIt.helper.AuthResponse;

public interface AuthService {

    AuthResponse signup(UserDto userDto) throws Exception;

    AuthResponse login(UserDto userDto) throws Exception;

}
