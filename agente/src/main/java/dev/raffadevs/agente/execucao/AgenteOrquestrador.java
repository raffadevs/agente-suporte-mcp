package dev.raffadevs.agente.execucao;

import dev.raffadevs.agente.sistemachamados.ChamadoDTO;
import dev.raffadevs.agente.sistemachamados.SistemaChamadosClient;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.stereotype.Service;

@Service
public class AgenteOrquestrador {

    private final ChatClient.Builder chatClientBuilder;
    private final ToolCallbackProvider toolCallbackProvider;
    private final ExecucaoAgenteRepository execucaoAgenteRepository;
    private final PassoAgenteRepository passoAgenteRepository;
    private final SistemaChamadosClient sistemaChamadosClient;

    public AgenteOrquestrador(
            ChatClient.Builder chatClientBuilder,
            ToolCallbackProvider toolCallbackProvider,
            ExecucaoAgenteRepository execucaoAgenteRepository,
            PassoAgenteRepository passoAgenteRepository,
            SistemaChamadosClient sistemaChamadosClient) {
        this.chatClientBuilder = chatClientBuilder;
        this.toolCallbackProvider = toolCallbackProvider;
        this.execucaoAgenteRepository = execucaoAgenteRepository;
        this.passoAgenteRepository = passoAgenteRepository;
        this.sistemaChamadosClient = sistemaChamadosClient;
    }

    public ExecucaoAgente iniciar(Long chamadoId) {
        ChamadoDTO chamado = sistemaChamadosClient.buscarChamado(chamadoId);
        return executarRaciocinio(chamadoId, MensagensAgente.mensagemInicial(chamado));
    }

    public ExecucaoAgente retomar(Long chamadoId, Long aprovacaoId, String decisao) {
        return executarRaciocinio(chamadoId, MensagensAgente.mensagemRetomada(chamadoId, aprovacaoId, decisao));
    }

    private ExecucaoAgente executarRaciocinio(Long chamadoId, String mensagemUsuario) {
        ExecucaoAgente execucao = execucaoAgenteRepository.save(new ExecucaoAgente(chamadoId));
        AtomicInteger contadorDePassos = new AtomicInteger(0);

        ToolCallback[] ferramentasAuditadas = Arrays.stream(toolCallbackProvider.getToolCallbacks())
                .map(tc -> (ToolCallback) new AuditandoToolCallback(tc, execucao.getId(), passoAgenteRepository, contadorDePassos))
                .toArray(ToolCallback[]::new);

        try {
            ChatResponse resposta = chatClientBuilder.build().prompt()
                    .system(MensagensAgente.PROMPT_SISTEMA)
                    .user(mensagemUsuario)
                    .toolCallbacks(ferramentasAuditadas)
                    .call()
                    .chatResponse();

            String textoFinal = resposta.getResult().getOutput().getText();
            execucao.concluir(textoFinal);
        } catch (RuntimeException ex) {
            execucao.falhar(ex.getMessage());
        }

        return execucaoAgenteRepository.save(execucao);
    }
}
