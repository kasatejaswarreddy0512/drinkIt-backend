package com.ktsr.drinkIt.service;

import com.ktsr.drinkIt.DTO.LoginRequestDto;
import com.ktsr.drinkIt.DTO.RegisterRequestDto;
import com.ktsr.drinkIt.DTO.UserDto;
import com.ktsr.drinkIt.helper.AuthResponse;

public interface AuthService {

    AuthResponse signup(RegisterRequestDto userDto) throws Exception;

    AuthResponse login(LoginRequestDto loginRequestDto) throws Exception;

}
