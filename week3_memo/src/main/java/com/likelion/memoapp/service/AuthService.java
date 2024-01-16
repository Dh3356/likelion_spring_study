package com.likelion.memoapp.service;


import com.likelion.memoapp.dto.auth.AuthLoginRequestDto;
import com.likelion.memoapp.dto.auth.AuthSignupRequestDto;
import com.likelion.memoapp.dto.response.ResponseDto;
import com.likelion.memoapp.model.Role;
import com.likelion.memoapp.model.User;
import com.likelion.memoapp.repository.UserRepository;
import com.likelion.memoapp.util.EncodeUtil;
import com.likelion.memoapp.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Collections;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "AUTH")
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional(readOnly = true)
    public ResponseEntity<ResponseDto<Void>> login(AuthLoginRequestDto authLoginRequestDTO,
                                                   HttpServletResponse response) {
        // 1. Login ID/PW 를 기반으로 Authentication 객체 생성
        // 이때 authentication 는 인증 여부를 확인하는 authenticated 값이 false
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                authLoginRequestDTO.getUserId(),
                authLoginRequestDTO.getPassword());

        // 2. 실제 검증 (사용자 비밀번호 체크)이 이루어지는 부분
        // authenticate 매서드가 실행될 때 CustomUserDetailsService 에서 만든 loadUserByUsername 메서드가 실행
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        String jwtToken = jwtTokenProvider.generateToken(authentication).getAccessToken();
        // 3. 인증 정보를 기반으로 JWT 토큰 생성, Cookie에 담아서 전달
        Cookie cookie = new Cookie("accessToken",
                EncodeUtil.encodeJwtBearerToken(jwtToken));

        cookie.setMaxAge(60 * 60 * 24);//하루
        cookie.setHttpOnly(true);//자바스크립트로 접근 불가
        cookie.setPath("/");//모든 경로에서 접근 가능
        response.addCookie(cookie);

        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그인 되었습니다."), HttpStatus.OK);
    }

    public ResponseEntity<ResponseDto<Void>> logout(HttpServletResponse response) {
        log.info("로그아웃 되었습니다.");
        Cookie cookie = new Cookie("accessToken", null);
        cookie.setMaxAge(0);// 쿠키 만료
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        response.addCookie(cookie);
        return new ResponseEntity<>(ResponseDto.res(HttpStatus.OK, "로그아웃 되었습니다."), HttpStatus.OK);
    }

    @Transactional
    public ResponseEntity<ResponseDto<Void>> signup(AuthSignupRequestDto authSignupRequestDTO) {
        if (this.isUserExists(authSignupRequestDTO.getUserId()) != null) {
            log.info("USER가 이미 존재합니다.");
            return new ResponseEntity<>(ResponseDto.res(HttpStatus.CONFLICT, "USER가 이미 존재합니다."), HttpStatus.CONFLICT);
        } else {
            User user = new User(authSignupRequestDTO.getUserId(), authSignupRequestDTO.getPassword(),
                    Collections.singleton(Role.ROLE_USER));
            this.userRepository.save(user);
            log.info("회원가입 되었습니다.");
            return new ResponseEntity<>(ResponseDto.res(HttpStatus.CREATED, "회원가입 되었습니다."), HttpStatus.CREATED);
        }
    }

    private User isUserExists(String userId) {
        return this.userRepository.findById(userId).orElse(null);
    }
}
