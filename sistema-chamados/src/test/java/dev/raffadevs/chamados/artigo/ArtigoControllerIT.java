package dev.raffadevs.chamados.artigo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import dev.raffadevs.chamados.IntegrationTestBase;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

class ArtigoControllerIT extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void retornaArtigosEncontradosPelaBuscaVetorial() {
        Document documento = Document.builder()
                .text("Se sua conta foi bloqueada, abra um chamado na categoria acesso.")
                .metadata(Map.of(ArtigoMetadata.ARTIGO_ID, 1, ArtigoMetadata.TITULO, "Como desbloquear minha conta"))
                .score(0.87)
                .build();
        when(vectorStore.similaritySearch(any(org.springframework.ai.vectorstore.SearchRequest.class)))
                .thenReturn(List.of(documento));

        ArtigoEncontrado[] encontrados = restTemplate.getForObject("/api/artigos/busca?q=conta bloqueada", ArtigoEncontrado[].class);

        assertThat(encontrados).hasSize(1);
        assertThat(encontrados[0].titulo()).isEqualTo("Como desbloquear minha conta");
        assertThat(encontrados[0].similaridade()).isEqualTo(0.87);
    }
}
