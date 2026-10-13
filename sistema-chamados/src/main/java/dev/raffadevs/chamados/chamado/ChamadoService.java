package dev.raffadevs.chamados.chamado;

import dev.raffadevs.chamados.common.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ChamadoService {

    private final ChamadoRepository chamadoRepository;

    public ChamadoService(ChamadoRepository chamadoRepository) {
        this.chamadoRepository = chamadoRepository;
    }

    @Transactional
    public Chamado abrir(Long solicitanteId, String titulo, String descricao, String categoria, Prioridade prioridade) {
        return chamadoRepository.save(new Chamado(solicitanteId, titulo, descricao, categoria, prioridade));
    }

    @Transactional(readOnly = true)
    public Chamado buscarOuFalhar(Long id) {
        return chamadoRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Chamado %d não encontrado".formatted(id)));
    }

    @Transactional
    public Chamado atualizar(Long id, StatusChamado novoStatus, String resolucao) {
        Chamado chamado = buscarOuFalhar(id);
        chamado.atualizarStatus(novoStatus, resolucao);
        return chamado;
    }
}
