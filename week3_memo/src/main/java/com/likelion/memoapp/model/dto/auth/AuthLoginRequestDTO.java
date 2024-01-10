package com.likelion.memoapp.model.dto.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequestDTO {
    @Email(message = "이메일 형식이 아닙니다.")
    private String email;

    @NotBlank
    private String password;
}
