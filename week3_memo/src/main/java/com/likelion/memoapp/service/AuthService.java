package com.likelion.memoapp.service;

import com.likelion.memoapp.model.User;
import com.likelion.memoapp.model.dto.auth.AuthLoginRequestDTO;
import com.likelion.memoapp.model.dto.auth.AuthSignupRequestDTO;
import com.likelion.memoapp.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "AUTH")
public class AuthService {
    private final UserRepository userRepository;

    @Autowired()
    public AuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void login(AuthLoginRequestDTO authLoginRequestDTO) {
        if (!this.isUserExists(authLoginRequestDTO.getEmail())) {
            log.info("USER가 존재하지 않습니다.");
        } else {
            log.info("USER가 존재합니다.");
        }
    }

    public void logout() {
        log.info("로그아웃 되었습니다.");
    }

    public void signup(AuthSignupRequestDTO authSignupRequestDTO) {
        if (this.isUserExists(authSignupRequestDTO.getEmail())) {
            log.info("USER가 이미 존재합니다.");
        } else {
            User user = new User(authSignupRequestDTO.getEmail(), authSignupRequestDTO.getPassword());
            this.userRepository.save(user);
            log.info("회원가입 되었습니다.");
        }
    }

    private boolean isUserExists(String email) {
        return this.userRepository.existsByEmail(email);
    }
}
