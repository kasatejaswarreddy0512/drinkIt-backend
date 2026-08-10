package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.DTO.LoginRequestDto;
import com.ktsr.drinkIt.DTO.RegisterRequestDto;
import com.ktsr.drinkIt.DTO.UserDto;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.AuthResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<ResponseWrapper> createUser(@Valid @RequestBody RegisterRequestDto userDto){
        try {
            AuthResponse authResponse=authService.signup(userDto);
            return APIResponse.get(ErrorCode.SUCCESS,authResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseWrapper> login(@RequestBody LoginRequestDto loginRequestDto){
        try {
            AuthResponse authResponse=authService.login(loginRequestDto);
            return APIResponse.get(ErrorCode.SUCCESS,authResponse,HttpStatus.CREATED);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
