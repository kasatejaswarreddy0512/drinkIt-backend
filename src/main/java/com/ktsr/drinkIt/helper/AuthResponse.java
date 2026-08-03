package com.ktsr.drinkIt.helper;

import com.ktsr.drinkIt.DTO.UserDto;
import lombok.Data;

@Data
public class AuthResponse {

    private String jwt;
    private String message;
    private UserDto user;

}
