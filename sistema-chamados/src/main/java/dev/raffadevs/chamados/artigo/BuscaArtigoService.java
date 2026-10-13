package dev.raffadevs.chamados.artigo;

import dev.raffadevs.chamados.config.BuscaProperties;
import java.util.List;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.SearchRequest;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

@Service
@EnableConfigurationProperties(BuscaProperties.class)
public class BuscaArtigoService {

    private final VectorStore vectorStore;
    private final BuscaProperties properties;

    public BuscaArtigoService(VectorStore vectorStore, BuscaProperties properties) {
        this.vectorStore = vectorStore;
        this.properties = properties;
    }

    public List<ArtigoEncontrado> buscar(String pergunta) {
        SearchRequest request = SearchRequest.builder()
                .query(pergunta)
                .topK(properties.getLimiteArtigos())
                .similarityThreshold(properties.getSimilaridadeMinima())
                .build();
        List<Document> encontrados = vectorStore.similaritySearch(request);
        return encontrados.stream()
                .map(doc -> new ArtigoEncontrado(
                        ((Number) doc.getMetadata().get(ArtigoMetadata.ARTIGO_ID)).longValue(),
                        (String) doc.getMetadata().get(ArtigoMetadata.TITULO),
                        doc.getText(),
                        doc.getScore() == null ? 0.0 : doc.getScore()))
                .toList();
    }
}
