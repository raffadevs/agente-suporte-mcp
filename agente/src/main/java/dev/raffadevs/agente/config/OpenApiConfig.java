package dev.raffadevs.agente.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI().info(new Info()
                .title("agente")
                .description("Agente que atende chamados chamando as ferramentas do mcp-server, com trilha de auditoria por passo")
                .version("v1"));
    }
}
