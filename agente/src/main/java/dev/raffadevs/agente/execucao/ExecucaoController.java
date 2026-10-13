package dev.raffadevs.agente.execucao;

import dev.raffadevs.agente.execucao.dto.ExecucaoResponse;
import dev.raffadevs.agente.execucao.dto.PassoResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chamados/{chamadoId}")
public class ExecucaoController {

    private final AgenteOrquestrador agenteOrquestrador;
    private final ExecucaoAgenteRepository execucaoAgenteRepository;
    private final PassoAgenteRepository passoAgenteRepository;

    public ExecucaoController(
            AgenteOrquestrador agenteOrquestrador,
            ExecucaoAgenteRepository execucaoAgenteRepository,
            PassoAgenteRepository passoAgenteRepository) {
        this.agenteOrquestrador = agenteOrquestrador;
        this.execucaoAgenteRepository = execucaoAgenteRepository;
        this.passoAgenteRepository = passoAgenteRepository;
    }

    @PostMapping("/iniciar")
    public ExecucaoResponse iniciar(@PathVariable Long chamadoId) {
        return paraResponse(agenteOrquestrador.iniciar(chamadoId));
    }

    @PostMapping("/retomar")
    public ExecucaoResponse retomar(
            @PathVariable Long chamadoId, @RequestParam Long aprovacaoId, @RequestParam String decisao) {
        return paraResponse(agenteOrquestrador.retomar(chamadoId, aprovacaoId, decisao));
    }

    @GetMapping("/execucoes")
    public List<ExecucaoResponse> listarExecucoes(@PathVariable Long chamadoId) {
        return execucaoAgenteRepository.findByChamadoIdOrderByIniciadaEmDesc(chamadoId).stream()
                .map(this::paraResponse)
                .toList();
    }

    private ExecucaoResponse paraResponse(ExecucaoAgente execucao) {
        List<PassoResponse> passos = passoAgenteRepository.findByExecucaoIdOrderByOrdemAsc(execucao.getId()).stream()
                .map(PassoResponse::from)
                .toList();
        return ExecucaoResponse.from(execucao, passos);
    }
}
