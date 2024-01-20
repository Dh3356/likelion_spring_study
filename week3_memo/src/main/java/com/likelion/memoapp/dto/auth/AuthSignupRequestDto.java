package com.likelion.memoapp.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthSignupRequestDto {
    @NotBlank
    private String userId;

    @NotBlank
    private String password;
}
