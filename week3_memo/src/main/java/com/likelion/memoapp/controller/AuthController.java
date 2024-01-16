package com.likelion.memoapp.controller;

import com.likelion.memoapp.dto.auth.AuthLoginRequestDto;
import com.likelion.memoapp.dto.auth.AuthSignupRequestDto;
import com.likelion.memoapp.dto.response.ResponseDto;
import com.likelion.memoapp.service.AuthService;
import com.likelion.memoapp.util.SecurityUtil;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("auth")
public class AuthController {
    private final AuthService authService;

    @Autowired()
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Void>> login(@RequestBody @Valid AuthLoginRequestDto authLoginRequestDTO,
                                                   HttpServletResponse response) {
        return this.authService.login(authLoginRequestDTO, response);
    }

    @PostMapping("/signup")
    public ResponseEntity<ResponseDto<Void>> signup(@RequestBody @Valid AuthSignupRequestDto authSignupRequestDTO) {
        return this.authService.signup(authSignupRequestDTO);
    }

    //UserId를 받아올 수 있다. (로그인한 사용자의 정보를 받아올 수 있다.)
    @GetMapping("/test")
    public String test() {
        return SecurityUtil.getCurrentUserId();
    }
}
