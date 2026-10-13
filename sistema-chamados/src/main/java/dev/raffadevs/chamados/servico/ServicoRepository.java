package dev.raffadevs.chamados.servico;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository<Servico, Long> {
    Optional<Servico> findByNome(String nome);
}
