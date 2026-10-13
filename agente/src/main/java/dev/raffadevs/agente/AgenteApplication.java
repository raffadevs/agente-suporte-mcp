package dev.raffadevs.agente;

import org.springframework.ai.model.ollama.autoconfigure.OllamaEmbeddingAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Este serviço só usa o Ollama para chat (raciocínio do agente), nunca para embeddings —
 * por isso a autoconfiguração de embedding é excluída: sem isso, o Spring Boot cria um bean
 * de embedding de qualquer forma, e ele checa a disponibilidade do modelo no Ollama ao subir
 * o contexto, uma dependência desnecessária para um serviço que nunca gera vetores.
 */
@SpringBootApplication(exclude = OllamaEmbeddingAutoConfiguration.class)
public class AgenteApplication {
    public static void main(String[] args) {
        SpringApplication.run(AgenteApplication.class, args);
    }
}
