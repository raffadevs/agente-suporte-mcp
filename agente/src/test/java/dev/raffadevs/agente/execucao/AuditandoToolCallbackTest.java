package dev.raffadevs.agente.execucao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.tool.ToolCallback;
import org.springframework.ai.tool.definition.ToolDefinition;

@ExtendWith(MockitoExtension.class)
class AuditandoToolCallbackTest {

    @Mock
    private ToolCallback delegate;

    @Mock
    private PassoAgenteRepository passoAgenteRepository;

    @Mock
    private ToolDefinition toolDefinition;

    private AuditandoToolCallback auditado;

    @BeforeEach
    void montar() {
        when(delegate.getToolDefinition()).thenReturn(toolDefinition);
        when(toolDefinition.name()).thenReturn("consultarUsuario");
        auditado = new AuditandoToolCallback(delegate, 42L, passoAgenteRepository, new AtomicInteger(0));
    }

    @Test
    void registraUmPassoComFerramentaEntradaESaidaAoChamarComSucesso() {
        when(delegate.call("{\"usuarioId\":9}")).thenReturn("{\"contaBloqueada\":true}");

        String resultado = auditado.call("{\"usuarioId\":9}");

        assertThat(resultado).isEqualTo("{\"contaBloqueada\":true}");
        ArgumentCaptor<PassoAgente> captor = ArgumentCaptor.forClass(PassoAgente.class);
        verify(passoAgenteRepository).save(captor.capture());
        PassoAgente passo = captor.getValue();
        assertThat(passo.getExecucaoId()).isEqualTo(42L);
        assertThat(passo.getOrdem()).isEqualTo(1);
        assertThat(passo.getFerramenta()).isEqualTo("consultarUsuario");
        assertThat(passo.getEntrada()).isEqualTo("{\"usuarioId\":9}");
        assertThat(passo.getSaida()).isEqualTo("{\"contaBloqueada\":true}");
    }

    @Test
    void registraUmPassoComErroEPropagaAExcecaoQuandoADelegacaoFalha() {
        when(delegate.call(any())).thenThrow(new IllegalStateException("usuário não encontrado"));

        assertThatThrownBy(() -> auditado.call("{\"usuarioId\":999}")).isInstanceOf(IllegalStateException.class);

        ArgumentCaptor<PassoAgente> captor = ArgumentCaptor.forClass(PassoAgente.class);
        verify(passoAgenteRepository).save(captor.capture());
        assertThat(captor.getValue().getSaida()).contains("ERRO").contains("usuário não encontrado");
    }

    @Test
    void incrementaAOrdemACadaChamada() {
        when(delegate.call(any())).thenReturn("ok");

        auditado.call("{}");
        auditado.call("{}");

        ArgumentCaptor<PassoAgente> captor = ArgumentCaptor.forClass(PassoAgente.class);
        verify(passoAgenteRepository, org.mockito.Mockito.times(2)).save(captor.capture());
        assertThat(captor.getAllValues()).extracting(PassoAgente::getOrdem).containsExactly(1, 2);
    }
}
