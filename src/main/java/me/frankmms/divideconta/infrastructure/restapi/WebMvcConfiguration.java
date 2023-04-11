package me.frankmms.divideconta.infrastructure.restapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfiguration {

    @Value("${cors.allowed-origins}")
    String allowedOrigins;

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                String[] allowedOriginsArray = WebMvcConfiguration.this.allowedOrigins.split(",");
                registry.addMapping("/**").allowedOrigins(allowedOriginsArray);
            }
        };
    }

}
