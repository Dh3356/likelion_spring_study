package com.likelion.memoapp.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.memoapp.dto.response.ResponseDto;
import com.likelion.memoapp.filter.JwtFilter;
import com.likelion.memoapp.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JwtTokenProvider jwtTokenProvider;
    private final CorsConfig corsConfig;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()
                        .anyRequest().authenticated()
                )
                .sessionManagement((sessionManagement) ->
                        sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .addFilter(corsConfig.corsFilter())
                .addFilterBefore(new JwtFilter(jwtTokenProvider),
                        UsernamePasswordAuthenticationFilter.class)
                .logout(logout -> {
                    logout
                            .logoutUrl("/auth/logout") // 로그아웃 URL 지정
                            .logoutSuccessHandler((request, response, authentication) -> {
                                Cookie cookie = new Cookie("accessToken", null);
                                cookie.setMaxAge(0);// 쿠키 만료
                                cookie.setHttpOnly(true);
                                cookie.setPath("/");
                                response.addCookie(cookie);

                                // 로그아웃 성공 시 200 OK 리턴
                                String jsonResponse = new ObjectMapper().writeValueAsString(
                                        ResponseDto.res(HttpStatus.OK, "로그아웃 되었습니다."));

                                // HTTP 상태 코드 200 OK, JSON 형식 리턴
                                response.setStatus(HttpStatus.OK.value());
                                response.setContentType("application/json;charset=UTF-8");//응답 데이터 타입 지정
                                response.getWriter().write(jsonResponse);//응답 데이터 출력
                                response.getWriter().flush();//즉시 응답(더 빠름)
                            })
                            .invalidateHttpSession(true); // HTTP 세션 무효화
                })
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
