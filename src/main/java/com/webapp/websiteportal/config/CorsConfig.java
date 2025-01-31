package com.webapp.websiteportal.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(@NonNull CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*");
    }
//	   @Override
//	    public void addCorsMappings(@NonNull CorsRegistry registry) {
//	        registry.addMapping("/openApi/**") // Apply CORS only to /openApi/** endpoints
//	                .allowedOrigins("*")       // Allow all origins (adjust as needed for production)
//	                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS"); // Restrict HTTP methods if needed
//	    }
}
