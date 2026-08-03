package com.ktsr.drinkIt.controller;

import com.ktsr.drinkIt.entity.User;
import com.ktsr.drinkIt.enums.ErrorCode;
import com.ktsr.drinkIt.helper.APIResponse;
import com.ktsr.drinkIt.helper.ResponseWrapper;
import com.ktsr.drinkIt.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/profile")
    public ResponseEntity<ResponseWrapper> getUserProfile(@RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user=userService.getUserFromJwtToken(jwt);
            return APIResponse.get(ErrorCode.SUCCESS,user,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper> getUserById(@PathVariable Long id,
                                               @RequestHeader("Authorization") String jwt) throws Exception {
        try {
            User user=userService.getUserById(id);
            if(user==null){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            return APIResponse.get(ErrorCode.SUCCESS,user,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper> updateUser(@PathVariable Long id,@Valid @RequestBody User user) throws Exception {
        try {
            boolean exists=userService.existsUser(id);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            User updateUser= userService.updateUser(id,user);
            return APIResponse.get(ErrorCode.SUCCESS,updateUser,HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper> deleteUser(@PathVariable Long id) throws Exception {
        try {
            boolean exists=userService.existsUser(id);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            userService.deleteUser(id);
            return APIResponse.get(ErrorCode.SUCCESS,"User deleted Successfully...!.", HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/activate")
    public ResponseEntity<ResponseWrapper> activateUser(@PathVariable Long id) throws Exception {
        try {
            boolean exists=userService.existsUser(id);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            userService.activateUser(id);
            return APIResponse.get(ErrorCode.SUCCESS,"User Activated Successfully...!.", HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ResponseWrapper> deactivateUser(@PathVariable Long id) throws Exception {
        try {
            boolean exists=userService.existsUser(id);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            userService.deactivateUser(id);
            return APIResponse.get(ErrorCode.SUCCESS,"User Deactivated Successfully...!.", HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}/verify")
    public ResponseEntity<ResponseWrapper> verifyUser(@PathVariable Long id) throws Exception {
        try {
            boolean exists=userService.existsUser(id);
            if(!exists){
                return APIResponse.get(ErrorCode.USER_NOT_FOUND,"User not found.", HttpStatus.NOT_FOUND);
            }
            userService.verifyUser(id);
            return APIResponse.get(ErrorCode.SUCCESS,"User Verified Successfully...!.", HttpStatus.OK);
        }
        catch(Exception e){
            return APIResponse.get(ErrorCode.INTERNAL_SERVER_ERROR,"Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}

