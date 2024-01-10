package com.likelion.memoapp.controller;

import com.likelion.memoapp.model.dto.auth.AuthLoginRequestDTO;
import com.likelion.memoapp.model.dto.auth.AuthSignupRequestDTO;
import com.likelion.memoapp.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @Autowired()
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public void login(@RequestBody @Valid AuthLoginRequestDTO authLoginRequestDTO) {
        this.authService.login(authLoginRequestDTO);
    }

    @PostMapping("/logout")
    public void logout() {
        this.authService.logout();
    }

    @PostMapping("/signup")
    public void signup(@RequestBody @Valid AuthSignupRequestDTO authSignupRequestDTO) {
        this.authService.signup(authSignupRequestDTO);
    }
}
