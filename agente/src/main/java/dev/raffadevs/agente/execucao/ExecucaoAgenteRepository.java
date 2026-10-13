package dev.raffadevs.agente.execucao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExecucaoAgenteRepository extends JpaRepository<ExecucaoAgente, Long> {
    List<ExecucaoAgente> findByChamadoIdOrderByIniciadaEmDesc(Long chamadoId);
}
