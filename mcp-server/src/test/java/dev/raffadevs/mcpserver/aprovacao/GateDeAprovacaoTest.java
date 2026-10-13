package dev.raffadevs.mcpserver.aprovacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.raffadevs.mcpserver.ferramentas.ResultadoFerramenta;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class GateDeAprovacaoTest {

    @Mock
    private SolicitacaoAprovacaoRepository repository;

    private GateDeAprovacao gate;

    @BeforeEach
    void montar() {
        gate = new GateDeAprovacao(repository, new ObjectMapper());
    }

    @Test
    void semAprovacaoIdCriaSolicitacaoPendenteENaoExecutaAAcao() {
        when(repository.save(any())).thenAnswer(invocacao -> {
            SolicitacaoAprovacao solicitacao = invocacao.getArgument(0);
            definirId(solicitacao, 7L);
            return solicitacao;
        });
        boolean[] executou = {false};

        ResultadoFerramenta resultado = gate.executar(
                "desbloquearConta", 1L, "Desbloquear conta do usuário 9", Map.of("usuarioId", 9L), null,
                () -> {
                    executou[0] = true;
                    return "ok";
                });

        assertThat(resultado.sucesso()).isFalse();
        assertThat(resultado.aguardandoAprovacao()).isTrue();
        assertThat(resultado.aprovacaoId()).isEqualTo("7");
        assertThat(executou[0]).isFalse();
    }

    @Test
    void comAprovacaoValidaEAindaNaoUsadaExecutaAAcaoEMarcaUsada() {
        SolicitacaoAprovacao solicitacao = new SolicitacaoAprovacao("desbloquearConta", 1L, "ação", "{}");
        solicitacao.decidir(StatusAprovacao.APROVADA, "analista@empresa.com");
        when(repository.findById(5L)).thenReturn(Optional.of(solicitacao));
        boolean[] executou = {false};

        ResultadoFerramenta resultado = gate.executar(
                "desbloquearConta", 1L, "ação", Map.of("usuarioId", 9L), "5",
                () -> {
                    executou[0] = true;
                    return "ok";
                });

        assertThat(resultado.sucesso()).isTrue();
        assertThat(executou[0]).isTrue();
        assertThat(solicitacao.isUsada()).isTrue();
    }

    @Test
    void comAprovacaoJaUsadaNaoExecutaDeNovo() {
        SolicitacaoAprovacao solicitacao = new SolicitacaoAprovacao("desbloquearConta", 1L, "ação", "{}");
        solicitacao.decidir(StatusAprovacao.APROVADA, "analista@empresa.com");
        solicitacao.marcarUsada();
        when(repository.findById(5L)).thenReturn(Optional.of(solicitacao));
        boolean[] executou = {false};

        ResultadoFerramenta resultado = gate.executar(
                "desbloquearConta", 1L, "ação", Map.of("usuarioId", 9L), "5",
                () -> {
                    executou[0] = true;
                    return "ok";
                });

        assertThat(resultado.sucesso()).isFalse();
        assertThat(executou[0]).isFalse();
    }

    @Test
    void comAprovacaoPendenteOuRejeitadaNaoExecuta() {
        SolicitacaoAprovacao solicitacao = new SolicitacaoAprovacao("desbloquearConta", 1L, "ação", "{}");
        solicitacao.decidir(StatusAprovacao.REJEITADA, "analista@empresa.com");
        when(repository.findById(5L)).thenReturn(Optional.of(solicitacao));
        boolean[] executou = {false};

        ResultadoFerramenta resultado = gate.executar(
                "desbloquearConta", 1L, "ação", Map.of("usuarioId", 9L), "5",
                () -> {
                    executou[0] = true;
                    return "ok";
                });

        assertThat(resultado.sucesso()).isFalse();
        assertThat(executou[0]).isFalse();
    }

    private void definirId(SolicitacaoAprovacao solicitacao, Long id) throws Exception {
        var campo = SolicitacaoAprovacao.class.getDeclaredField("id");
        campo.setAccessible(true);
        campo.set(solicitacao, id);
    }
}
