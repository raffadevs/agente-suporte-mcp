package dev.raffadevs.chamados.artigo;

import java.util.List;
import java.util.Map;
import org.springframework.ai.document.Document;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class ArtigoIndexador {

    private final ArtigoRepository artigoRepository;
    private final VectorStore vectorStore;

    public ArtigoIndexador(ArtigoRepository artigoRepository, VectorStore vectorStore) {
        this.artigoRepository = artigoRepository;
        this.vectorStore = vectorStore;
    }

    @EventListener(ApplicationReadyEvent.class)
    @Transactional
    public void indexarArtigosPendentes() {
        List<Artigo> pendentes = artigoRepository.findByIndexadoFalse();
        for (Artigo artigo : pendentes) {
            Document documento = new Document(artigo.getConteudo(), Map.of(
                    ArtigoMetadata.ARTIGO_ID, artigo.getId(),
                    ArtigoMetadata.TITULO, artigo.getTitulo()));
            vectorStore.add(List.of(documento));
            artigo.marcarIndexado();
        }
    }
}
