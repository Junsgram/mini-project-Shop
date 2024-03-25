package org.practice.shop.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${uploadPath}")
    String uploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 웹 브라우저에서 입력하는 url에 /images 로 시작하는 경우 uploadPath 프로퍼티 값으 읽고
        // 설정한 폴더를 기준으로 값을 읽어오도록 설정
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}
