package dev.raffadevs.agente.execucao;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PassoAgenteRepository extends JpaRepository<PassoAgente, Long> {
    List<PassoAgente> findByExecucaoIdOrderByOrdemAsc(Long execucaoId);
}
