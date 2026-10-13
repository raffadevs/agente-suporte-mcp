package dev.raffadevs.agente;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.mcp.client.autoconfigure.McpClientAutoConfiguration;
import org.springframework.ai.mcp.client.autoconfigure.McpToolCallbackAutoConfiguration;
import org.springframework.ai.mcp.client.autoconfigure.SseHttpClientTransportAutoConfiguration;
import org.springframework.ai.model.chat.client.autoconfigure.ChatClientAutoConfiguration;
import org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration;
import org.springframework.ai.tool.StaticToolCallbackProvider;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

/**
 * As integrações reais (MCP client -> mcp-server, chat model -> Ollama) exigem uma rede que
 * não existe no pipeline de CI deste projeto — mesma limitação registrada no projeto
 * assistente-de-licitacoes-ia. Os testes de integração aqui desligam a autoconfiguração real
 * do cliente MCP e do modelo de chat (ambas tentariam uma conexão de rede de verdade ao
 * subir o contexto) e substituem por dublês; a validação do laço de raciocínio com um
 * modelo real é feita manualmente via docker compose.
 */
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@EnableAutoConfiguration(exclude = {
        McpClientAutoConfiguration.class,
        McpToolCallbackAutoConfiguration.class,
        SseHttpClientTransportAutoConfiguration.class,
        OllamaChatAutoConfiguration.class,
        ChatClientAutoConfiguration.class
})
public abstract class IntegrationTestBase {

    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("agente")
            .withUsername("agente")
            .withPassword("agente");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void propriedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestConfig {

        @Bean
        public ToolCallbackProvider toolCallbackProvider() {
            return new StaticToolCallbackProvider();
        }

        @Bean
        public ChatClient.Builder chatClientBuilder() {
            return org.mockito.Mockito.mock(ChatClient.Builder.class);
        }

        @Bean
        @Primary
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().requestFactory(() ->
                    new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
        }
    }
}
