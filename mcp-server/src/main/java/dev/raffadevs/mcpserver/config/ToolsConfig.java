package dev.raffadevs.mcpserver.config;

import dev.raffadevs.mcpserver.ferramentas.FerramentasEscritaService;
import dev.raffadevs.mcpserver.ferramentas.FerramentasLeituraService;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ToolsConfig {

    @Bean
    public ToolCallbackProvider ferramentasDoSuporte(
            FerramentasLeituraService leitura, FerramentasEscritaService escrita) {
        return MethodToolCallbackProvider.builder().toolObjects(leitura, escrita).build();
    }
}
