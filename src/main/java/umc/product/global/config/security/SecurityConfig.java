package umc.product.global.config.security;

import org.springframework.http.MediaType;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import umc.product.domain.member.entity.enums.Role;
import umc.product.domain.member.service.member.MemberService;
import umc.product.global.config.security.auth.CustomAccessDeniedHandler;
import umc.product.global.config.security.jwt.JwtAuthenticationFilter;
import umc.product.global.config.security.jwt.JwtExceptionFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import umc.product.global.config.security.jwt.JwtProvider;

import java.time.LocalDateTime;

import static umc.product.domain.member.status.AuthErrorStatus.INVALID_ACCESS_TOKEN;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtProvider jwtProvider;
    private final MemberService memberService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
/*
    private final PrincipalDetailsService principalDetailsService;
*/


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configure(http))
                //.userDetailsService(principalDetailsService)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html", "/webjars/**").permitAll()
                        .requestMatchers("/s3/**").permitAll()
                        .requestMatchers("/members/signup", "/members/auth/login").permitAll()
                        .requestMatchers("/members/login").permitAll()
                        //회원가입, 로그인
                        .requestMatchers("/web/admin/auth/login", "/web/admin/auth/signup", "web/admin/members/code/verify" ,"web/admin/members/create/university-code").permitAll()
                        .requestMatchers("/members/auth/signup", "/members/auth/social/login", "/members/auth/token/refresh", "members/code/verify").permitAll()

                        //Challenger
                        .requestMatchers("/suggestion/**").hasAnyAuthority("ROLE_"+Role.SCHOOL_ADMIN, "ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN, "ROLE_"+Role.CHALLENGER, "ROLE_"+Role.UNIVERSITY_STAFF)
                        .requestMatchers("/members/**").hasAnyAuthority("ROLE_"+Role.SCHOOL_ADMIN, "ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN, "ROLE_"+Role.CHALLENGER, "ROLE_"+Role.UNIVERSITY_STAFF)

                        //Admin Web Security
                        .requestMatchers("/web/admin/**").hasAnyAuthority("ROLE_"+Role.SCHOOL_ADMIN, "ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling ->
                        exceptionHandling.accessDeniedHandler(customAccessDeniedHandler)
                                .authenticationEntryPoint(authenticationEntryPoint()))
                .addFilterBefore(new JwtExceptionFilter(), LogoutFilter.class) // filter 등록시 등록되어있는 필터와 순서를 정의해야함
                .addFilterBefore(new JwtAuthenticationFilter(jwtProvider, memberService), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            response.setStatus(INVALID_ACCESS_TOKEN.getHttpStatus().value()); // 401
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setCharacterEncoding("UTF-8");
            response.getWriter().write(String.format(
                    "{\"timestamp\": \"%s\", \"code\": \"%s\", \"message\": \"%s\"}",
                    LocalDateTime.now(),
                    INVALID_ACCESS_TOKEN.getCode().getCode(),
                    INVALID_ACCESS_TOKEN.getMessage()
            ));
        };
    }
}