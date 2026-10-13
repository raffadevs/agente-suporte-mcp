package dev.raffadevs.mcpserver.aprovacao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SolicitacaoAprovacaoRepository extends JpaRepository<SolicitacaoAprovacao, Long> {
    List<SolicitacaoAprovacao> findByStatusOrderByCriadoEmDesc(StatusAprovacao status);
}
