package dev.raffadevs.chamados;

import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(IntegrationTestBase.TestRestTemplateConfig.class)
public abstract class IntegrationTestBase {

    @MockBean
    protected VectorStore vectorStore;

    static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>(DockerImageName.parse("postgres:16-alpine"))
            .withDatabaseName("chamados")
            .withUsername("chamados")
            .withPassword("chamados");

    static {
        POSTGRES.start();
    }

    @DynamicPropertySource
    static void propriedades(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES::getJdbcUrl);
        registry.add("spring.datasource.username", POSTGRES::getUsername);
        registry.add("spring.datasource.password", POSTGRES::getPassword);
        registry.add("spring.ai.vectorstore.pgvector.initialize-schema", () -> "false");
    }

    @org.springframework.boot.test.context.TestConfiguration
    static class TestRestTemplateConfig {
        @Bean
        @Primary
        public RestTemplateBuilder restTemplateBuilder() {
            return new RestTemplateBuilder().requestFactory(() ->
                    new HttpComponentsClientHttpRequestFactory(HttpClients.createDefault()));
        }
    }
}
