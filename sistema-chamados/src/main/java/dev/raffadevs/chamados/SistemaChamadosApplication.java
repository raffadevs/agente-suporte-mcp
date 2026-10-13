package dev.raffadevs.chamados;

import org.springframework.ai.model.ollama.autoconfigure.OllamaChatAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Este serviço só usa o Ollama para embeddings (busca semântica de artigos), nunca para
 * chat — por isso a autoconfiguração de chat é excluída: sem isso, o Spring Boot cria um
 * bean de chat de qualquer forma, e ele checa a disponibilidade do modelo no Ollama ao
 * subir o contexto, uma dependência desnecessária para um serviço que nunca gera texto.
 */
@SpringBootApplication(exclude = OllamaChatAutoConfiguration.class)
public class SistemaChamadosApplication {
    public static void main(String[] args) {
        SpringApplication.run(SistemaChamadosApplication.class, args);
    }
}
