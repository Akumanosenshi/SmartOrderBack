package ynov.smartorder.api.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Toutes les routes
                .allowedOrigins("http://localhost:8100") // Origine du frontend Angular
                .allowedMethods("*") // GET, POST, PUT, DELETE
                .allowedHeaders("*"); // Tous les headers
    }
}
