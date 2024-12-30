package org.example.payroll_management;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class config implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS for all endpoints from a specific origin
        registry.addMapping("/**")
                .allowedOrigins("*")  // Replace with your frontend URL
                .allowedMethods("GET", "POST", "PUT", "DELETE");
    }
}

