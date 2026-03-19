package com.library.project.library.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CustomConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // [설명] 브라우저에서 /display/** 로 시작하는 주소로 요청이 오면
        // 실제 내 컴퓨터의 C:/upload/ 폴더에서 파일을 찾으라는 설정이야!
        registry.addResourceHandler("/display/**")
                .addResourceLocations("file:///C:/upload/");
    }
}