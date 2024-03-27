package org.practice.shop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity // 스프링 시큐리티 필터 체인이 자동으로 포함하는 어노테이션
public class SecurityConfig {
    // field

    // constructor

    // method
    // 힙에 암호화인코더를 생성
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // 로그인 처리
        // loginPage("") : 사용자가 지정한 로그인 페이지
        http.formLogin(login -> login.loginPage("/member/login")
                // 로그인 성공 시 이동할 페이지
                .defaultSuccessUrl("/",true)
                // form태그의 action에 지정한 경로와 동일하게 진행
                .loginProcessingUrl("/loginProc")
                // 로그인 실패할 경우 이동될 페이지 경로
                .failureUrl("/member/login/error")
        );
        // 로그아웃
        http.logout((auth) -> auth.logoutUrl("/logout")
                .logoutSuccessUrl("/")
        );
        // 경로 권한 지정
        http.authorizeHttpRequests((auth)->auth
                        .requestMatchers("/","/member/**","/item/**","/images/**").permitAll()
                .requestMatchers("/admin/**").hasRole("ADMIN")
                .requestMatchers("/js/**","/css/**","/img/**").permitAll()
                .requestMatchers("/my/**").hasAnyRole("ADMIN","USER")
                .anyRequest().authenticated());
        // csrf토큰 체크 사용 안함
        http.csrf(cs -> cs.disable());
        return http.build();
    }
}
