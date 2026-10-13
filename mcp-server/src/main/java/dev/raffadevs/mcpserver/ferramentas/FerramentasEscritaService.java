package dev.raffadevs.mcpserver.ferramentas;

import dev.raffadevs.mcpserver.aprovacao.GateDeAprovacao;
import dev.raffadevs.mcpserver.sistemachamados.SistemaChamadosClient;
import java.util.Map;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * Ferramentas de escrita: toda chamada passa pelo {@link GateDeAprovacao}. Sem um
 * aprovacaoId válido (de uma solicitação já aprovada e não usada), a ação real nunca
 * chega a ser executada contra o sistema de chamados.
 */
@Service
public class FerramentasEscritaService {

    private final GateDeAprovacao gateDeAprovacao;
    private final SistemaChamadosClient sistemaChamadosClient;

    public FerramentasEscritaService(GateDeAprovacao gateDeAprovacao, SistemaChamadosClient sistemaChamadosClient) {
        this.gateDeAprovacao = gateDeAprovacao;
        this.sistemaChamadosClient = sistemaChamadosClient;
    }

    @Tool(name = "abrirChamado", description =
            "Abre um novo chamado, por exemplo para escalar um problema a um time humano. Ação de escrita: exige aprovação.")
    public ResultadoFerramenta abrirChamado(
            @ToolParam(description = "Id do chamado que originou esta abertura, se houver, ou nulo") Long chamadoOrigemId,
            @ToolParam(description = "Id do usuário solicitante") Long solicitanteId,
            @ToolParam(description = "Título do novo chamado") String titulo,
            @ToolParam(description = "Descrição do novo chamado") String descricao,
            @ToolParam(description = "Categoria do novo chamado") String categoria,
            @ToolParam(required = false, description = "Id da aprovação já concedida para esta ação, se houver") String aprovacaoId) {

        String acaoProposta = "Abrir chamado '%s' para o usuário %d na categoria '%s'.".formatted(titulo, solicitanteId, categoria);
        Map<String, Object> parametros = Map.of(
                "solicitanteId", solicitanteId, "titulo", titulo, "descricao", descricao, "categoria", categoria);

        return gateDeAprovacao.executar("abrirChamado", chamadoOrigemId, acaoProposta, parametros, aprovacaoId,
                () -> sistemaChamadosClient.abrirChamado(solicitanteId, titulo, descricao, categoria, "MEDIA"));
    }

    @Tool(name = "atualizarChamado", description =
            "Atualiza o status e a resolução de um chamado existente. Ação de escrita: exige aprovação.")
    public ResultadoFerramenta atualizarChamado(
            @ToolParam(description = "Id do chamado a atualizar") Long chamadoId,
            @ToolParam(description = "Novo status: EM_ATENDIMENTO, AGUARDANDO_APROVACAO, RESOLVIDO ou FECHADO") String novoStatus,
            @ToolParam(description = "Texto da resolução a registrar no chamado") String resolucao,
            @ToolParam(required = false, description = "Id da aprovação já concedida para esta ação, se houver") String aprovacaoId) {

        String acaoProposta = "Atualizar chamado %d para status '%s' com resolução: %s".formatted(chamadoId, novoStatus, resolucao);
        Map<String, Object> parametros = Map.of("chamadoId", chamadoId, "novoStatus", novoStatus, "resolucao", resolucao);

        return gateDeAprovacao.executar("atualizarChamado", chamadoId, acaoProposta, parametros, aprovacaoId,
                () -> sistemaChamadosClient.atualizarChamado(chamadoId, novoStatus, resolucao));
    }

    @Tool(name = "desbloquearConta", description =
            "Desbloqueia a conta de um usuário. Ação de escrita sensível: exige aprovação.")
    public ResultadoFerramenta desbloquearConta(
            @ToolParam(description = "Id do chamado relacionado a esta ação") Long chamadoId,
            @ToolParam(description = "Id do usuário cuja conta será desbloqueada") Long usuarioId,
            @ToolParam(required = false, description = "Id da aprovação já concedida para esta ação, se houver") String aprovacaoId) {

        String acaoProposta = "Desbloquear a conta do usuário %d.".formatted(usuarioId);
        Map<String, Object> parametros = Map.of("usuarioId", usuarioId);

        return gateDeAprovacao.executar("desbloquearConta", chamadoId, acaoProposta, parametros, aprovacaoId,
                () -> sistemaChamadosClient.desbloquearUsuario(usuarioId));
    }
}
