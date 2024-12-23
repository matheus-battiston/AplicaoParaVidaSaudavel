package br.com.cwi.crescer.api.security.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class    WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("http://localhost:3000", "https://alexandria.cwi.com.br'")
                .allowCredentials(true)
                .allowedMethods("*")
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
