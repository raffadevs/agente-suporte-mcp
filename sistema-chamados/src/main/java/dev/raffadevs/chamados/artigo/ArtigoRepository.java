package dev.raffadevs.chamados.artigo;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtigoRepository extends JpaRepository<Artigo, Long> {
    List<Artigo> findByIndexadoFalse();
}
