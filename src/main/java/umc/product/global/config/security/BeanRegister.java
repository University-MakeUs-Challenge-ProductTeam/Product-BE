package umc.product.global.config.security;

import umc.product.domain.member.serviceImpl.MemberServiceImpl;
import umc.product.global.config.security.auth.CustomAccessDeniedHandler;
import umc.product.global.config.security.jwt.JwtAuthenticationFilter;
import umc.product.global.config.security.jwt.JwtExceptionFilter;
import umc.product.global.config.security.jwt.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BeanRegister {

    @Bean
    public JwtProvider jwtProvider() {
        return new JwtProvider();
    }

    @Bean
    public CustomAccessDeniedHandler customAccessDeniedHandler() {
        return new CustomAccessDeniedHandler();
    }

    @Bean
    public JwtExceptionFilter jwtExceptionFilter() {
        return new JwtExceptionFilter();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtProvider jwtProvider, MemberServiceImpl memberService) {
        return new JwtAuthenticationFilter(jwtProvider, memberService);
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
