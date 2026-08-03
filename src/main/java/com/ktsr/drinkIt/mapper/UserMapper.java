package com.ktsr.drinkIt.mapper;

import com.ktsr.drinkIt.DTO.UserDto;
import com.ktsr.drinkIt.entity.User;

public class UserMapper {
    public static UserDto toDto(User user) {
        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .phone(user.getPhone())
                .role(user.getRole())
                .dateOfBirth(user.getDateOfBirth())
                .role(user.getRole())
                .gender(user.getGender())
                .active(user.getActive())
                .verified(user.getVerified())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .lastLoginAt(user.getLastLoginAt())
                .build();
    }
}
