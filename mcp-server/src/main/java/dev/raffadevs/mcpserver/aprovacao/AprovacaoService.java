package dev.raffadevs.mcpserver.aprovacao;

import dev.raffadevs.mcpserver.common.RecursoNaoEncontradoException;
import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AprovacaoService {

    private final SolicitacaoAprovacaoRepository repository;

    public AprovacaoService(SolicitacaoAprovacaoRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<SolicitacaoAprovacao> listarPendentes() {
        return repository.findByStatusOrderByCriadoEmDesc(StatusAprovacao.PENDENTE);
    }

    @Transactional(readOnly = true)
    public SolicitacaoAprovacao buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de aprovação %d não encontrada".formatted(id)));
    }

    /**
     * Só persiste a decisão. Quem chama este método (o controller) é responsável por
     * notificar o agente DEPOIS que esta chamada retornar — isto é, depois que a transação
     * já tiver comitado. Notificar de dentro desta mesma transação arriscaria o agente
     * chamar de volta a ferramenta antes do commit, sem ver a decisão ainda.
     */
    @Transactional
    public SolicitacaoAprovacao decidir(Long id, StatusAprovacao decisao, String decididoPor) {
        SolicitacaoAprovacao solicitacao = buscarOuFalhar(id);
        solicitacao.decidir(decisao, decididoPor);
        return solicitacao;
    }
}
