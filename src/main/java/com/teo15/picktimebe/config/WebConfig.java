package com.teo15.picktimebe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://pick-time.vercel.app", "http://192.168.219.101:3000", "http://localhost:3000")
                .allowedMethods("*")
                .allowedHeaders("Content-Type")
                .exposedHeaders("Content-Type")
                .allowCredentials(true)
                .maxAge(3000);
    }
}