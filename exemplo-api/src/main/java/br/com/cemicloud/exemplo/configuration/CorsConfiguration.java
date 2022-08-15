package br.com.cemicloud.exemplo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("POST", "GET", "PUT", "OPTIONS", "DELETE", "PATCH")
                .allowedOriginPatterns("*")
                .maxAge(3600)
                .allowedHeaders("Origin", "X-Requested-With", "Content-Type", "Accept", "Accept-Encoding", "Content-Encoding", "X-Auth-Token");
    }

}