package dev.raffadevs.mcpserver.aprovacao;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.raffadevs.mcpserver.common.RecursoNaoEncontradoException;
import dev.raffadevs.mcpserver.ferramentas.ResultadoFerramenta;
import java.util.Map;
import java.util.function.Supplier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ponto único por onde toda ferramenta de escrita passa. A segurança fica aqui, no servidor
 * MCP, e não no prompt do modelo: sem um aprovacaoId que aponte para uma solicitação já
 * aprovada e ainda não usada, a ação real nunca é executada — mesmo que o modelo informe
 * um id inventado ou tente chamar a ferramenta de novo sem esperar.
 */
@Service
public class GateDeAprovacao {

    private final SolicitacaoAprovacaoRepository repository;
    private final ObjectMapper objectMapper;

    public GateDeAprovacao(SolicitacaoAprovacaoRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Transactional
    public <T> ResultadoFerramenta executar(
            String ferramenta, Long chamadoId, String acaoProposta, Map<String, Object> parametros,
            String aprovacaoId, Supplier<T> acaoReal) {

        if (aprovacaoId == null || aprovacaoId.isBlank()) {
            SolicitacaoAprovacao solicitacao =
                    new SolicitacaoAprovacao(ferramenta, chamadoId, acaoProposta, escreverJson(parametros));
            repository.save(solicitacao);
            return ResultadoFerramenta.aguardandoAprovacao(solicitacao.getId().toString(), acaoProposta);
        }

        SolicitacaoAprovacao solicitacao = repository.findById(Long.valueOf(aprovacaoId))
                .orElseThrow(() -> new RecursoNaoEncontradoException("Solicitação de aprovação %s não encontrada".formatted(aprovacaoId)));

        if (!solicitacao.podeSerExecutada()) {
            return ResultadoFerramenta.falha(
                    "Aprovação %s não pode ser executada (status=%s, usada=%s).".formatted(
                            aprovacaoId, solicitacao.getStatus(), solicitacao.isUsada()));
        }

        solicitacao.marcarUsada();
        T resultado = acaoReal.get();
        return ResultadoFerramenta.sucesso(resultado);
    }

    private String escreverJson(Map<String, Object> parametros) {
        try {
            return objectMapper.writeValueAsString(parametros);
        } catch (JsonProcessingException ex) {
            throw new IllegalArgumentException("Não foi possível serializar os parâmetros da ferramenta", ex);
        }
    }
}
