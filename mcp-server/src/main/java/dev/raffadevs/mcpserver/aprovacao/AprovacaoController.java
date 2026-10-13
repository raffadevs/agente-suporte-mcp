package dev.raffadevs.mcpserver.aprovacao;

import dev.raffadevs.mcpserver.agente.AgenteClient;
import dev.raffadevs.mcpserver.aprovacao.dto.DecidirAprovacaoRequest;
import dev.raffadevs.mcpserver.aprovacao.dto.SolicitacaoAprovacaoResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/aprovacoes")
public class AprovacaoController {

    private final AprovacaoService aprovacaoService;
    private final AgenteClient agenteClient;

    public AprovacaoController(AprovacaoService aprovacaoService, AgenteClient agenteClient) {
        this.aprovacaoService = aprovacaoService;
        this.agenteClient = agenteClient;
    }

    @GetMapping
    public List<SolicitacaoAprovacaoResponse> listarPendentes() {
        return aprovacaoService.listarPendentes().stream().map(SolicitacaoAprovacaoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public SolicitacaoAprovacaoResponse buscar(@PathVariable Long id) {
        return SolicitacaoAprovacaoResponse.from(aprovacaoService.buscarOuFalhar(id));
    }

    @PostMapping("/{id}/decidir")
    public SolicitacaoAprovacaoResponse decidir(@PathVariable Long id, @Valid @RequestBody DecidirAprovacaoRequest request) {
        StatusAprovacao decisao = StatusAprovacao.valueOf(request.decisao().toUpperCase());
        SolicitacaoAprovacao solicitacao = aprovacaoService.decidir(id, decisao, request.decididoPor());
        agenteClient.notificarDecisao(solicitacao.getChamadoId(), solicitacao.getId(), decisao.name());
        return SolicitacaoAprovacaoResponse.from(solicitacao);
    }
}
