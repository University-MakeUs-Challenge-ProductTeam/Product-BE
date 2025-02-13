package umc.product.global.config.security;

import umc.product.domain.member.entity.enums.Role;
import umc.product.global.config.security.auth.CustomAccessDeniedHandler;
import umc.product.global.config.security.auth.PrincipalDetailsService;
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
                        .requestMatchers("/members/signup").permitAll()
                        .requestMatchers("/members/login").permitAll()
                        .requestMatchers("/admin/**", "members/admin/**").hasAuthority("ROLE_"+Role.ADMIN)
                        .requestMatchers("web/central-admin/**").hasAnyAuthority("ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN)
                        .requestMatchers("web/branch-admin/**").hasAnyAuthority("ROLE_"+Role.BRANCH_ADMIN, "ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN)
                        .requestMatchers("web/university-admin/**", "members/admin/generate/code").hasAnyAuthority("ROLE_"+Role.UNIVERSITY_ADMIN, "ROLE_"+Role.CENTRAL_ADMIN, "ROLE_"+Role.ADMIN)
                        .anyRequest().authenticated()
                )
                .exceptionHandling(exceptionHandling -> exceptionHandling.accessDeniedHandler(customAccessDeniedHandler))
                .addFilterBefore(jwtExceptionFilter, LogoutFilter.class) // filter 등록시 등록되어있는 필터와 순서를 정의해야함
                .addFilterBefore(jwtAuthenticationFilter, LogoutFilter.class)
                .build();
    }




}