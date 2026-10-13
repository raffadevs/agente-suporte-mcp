package dev.raffadevs.mcpserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final String[] origensPermitidas;

    public WebConfig(@Value("${app.cors.allowed-origins}") String origensPermitidas) {
        this.origensPermitidas = origensPermitidas.split(",");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins(origensPermitidas)
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE");
    }
}
