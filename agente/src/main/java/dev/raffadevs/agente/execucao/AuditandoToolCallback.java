package dev.raffadevs.agente.execucao;

import java.util.concurrent.atomic.AtomicInteger;
import org.springframework.ai.chat.model.ToolContext;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

/**
 * Decora cada {@link ToolCallback} descoberto no mcp-server para registrar um
 * {@link PassoAgente} a cada chamada real de ferramenta — é assim que a trilha de
 * auditoria é gravada, sem precisar reimplementar o laço de chamada de ferramentas do
 * Spring AI: ele continua decidindo quando chamar, isto aqui só observa cada chamada.
 */
public class AuditandoToolCallback implements ToolCallback {

    private final ToolCallback delegate;
    private final Long execucaoId;
    private final PassoAgenteRepository passoAgenteRepository;
    private final AtomicInteger contadorDePassos;

    public AuditandoToolCallback(
            ToolCallback delegate, Long execucaoId, PassoAgenteRepository passoAgenteRepository, AtomicInteger contadorDePassos) {
        this.delegate = delegate;
        this.execucaoId = execucaoId;
        this.passoAgenteRepository = passoAgenteRepository;
        this.contadorDePassos = contadorDePassos;
    }

    @Override
    public ToolDefinition getToolDefinition() {
        return delegate.getToolDefinition();
    }

    @Override
    public String call(String toolInput) {
        return registrarEChamar(toolInput, () -> delegate.call(toolInput));
    }

    @Override
    public String call(String toolInput, ToolContext toolContext) {
        return registrarEChamar(toolInput, () -> delegate.call(toolInput, toolContext));
    }

    private String registrarEChamar(String toolInput, java.util.function.Supplier<String> chamada) {
        int ordem = contadorDePassos.incrementAndGet();
        String ferramenta = delegate.getToolDefinition().name();
        try {
            String saida = chamada.get();
            passoAgenteRepository.save(new PassoAgente(execucaoId, ordem, ferramenta, toolInput, saida));
            return saida;
        } catch (RuntimeException ex) {
            passoAgenteRepository.save(
                    new PassoAgente(execucaoId, ordem, ferramenta, toolInput, "ERRO: " + ex.getMessage()));
            throw ex;
        }
    }
}
