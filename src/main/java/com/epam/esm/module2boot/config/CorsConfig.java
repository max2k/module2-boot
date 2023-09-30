package com.epam.esm.module2boot.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("http://localhost:3000"); // Replace with your frontend's origin URL
        config.addAllowedMethod("DELETE"); // Allow DELETE method
        config.addAllowedMethod("PUT"); // Allow DELETE method
        config.addAllowedMethod("GET"); // Allow GET method
        config.addAllowedMethod("OPTIONS"); // Allow GET method
        config.addAllowedMethod("POST"); // Allow GET method
        config.addAllowedHeader("*"); // Allow all headers

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
