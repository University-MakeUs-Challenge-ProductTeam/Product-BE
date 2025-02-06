package com.example.groutine.global.config.security;

import com.example.groutine.domain.member.entity.Role;
import com.example.groutine.global.config.security.auth.CustomAccessDeniedHandler;
import com.example.groutine.global.config.security.auth.PrincipalDetailsService;
import com.example.groutine.global.config.security.jwt.JwtAuthenticationFilter;
import com.example.groutine.global.config.security.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtExceptionFilter jwtExceptionFilter;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final PrincipalDetailsService principalDetailsService;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .userDetailsService(principalDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/s3/**").permitAll()
                        .requestMatchers("/members/sign-up").permitAll()
                        .requestMatchers("/members/login").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("ROLE_"+Role.ADMIN)
                        .requestMatchers("/owner/**").hasAuthority("ROLE_"+Role.BUSINESS)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtExceptionFilter, LogoutFilter.class) // filter 등록시 등록되어있는 필터와 순서를 정의해야함
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                .build();
    }




}