package com.ejemplo.cine; 

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Aplica a todos los endpoints que empiezan con /api/
                        .allowedOrigins(
                                "http://127.0.0.1:5500", // Live Server (VS Code)
                                "http://localhost:5500", // Otra forma común
                                "http://127.0.0.1:8080", // Si pruebas desde el mismo puerto
                                "http://localhost:8080", // Útil si sirves HTML desde Spring Boot
                                "null" // Para file:// (si abres el HTML directamente)
                )
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };
    }
}