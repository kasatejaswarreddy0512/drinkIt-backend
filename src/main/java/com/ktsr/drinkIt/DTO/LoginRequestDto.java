package com.ktsr.drinkIt.DTO;

import lombok.*;

@Data@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDto {
    private String email;
    private String password;
}
