package dev.raffadevs.chamados.chamado;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class ChamadoTest {

    @Test
    void comecaAbertoAoCriar() {
        Chamado chamado = new Chamado(1L, "Título", "Descrição", "acesso", Prioridade.ALTA);

        assertThat(chamado.getStatus()).isEqualTo(StatusChamado.ABERTO);
        assertThat(chamado.getResolucao()).isNull();
    }

    @Test
    void atualizarStatusGravaResolucaoQuandoInformada() {
        Chamado chamado = new Chamado(1L, "Título", "Descrição", "acesso", Prioridade.ALTA);

        chamado.atualizarStatus(StatusChamado.RESOLVIDO, "Conta desbloqueada após confirmação de identidade.");

        assertThat(chamado.getStatus()).isEqualTo(StatusChamado.RESOLVIDO);
        assertThat(chamado.getResolucao()).isEqualTo("Conta desbloqueada após confirmação de identidade.");
    }

    @Test
    void atualizarStatusMantemResolucaoAnteriorQuandoNaoInformada() {
        Chamado chamado = new Chamado(1L, "Título", "Descrição", "acesso", Prioridade.ALTA);
        chamado.atualizarStatus(StatusChamado.EM_ATENDIMENTO, "Em análise pelo agente.");

        chamado.atualizarStatus(StatusChamado.AGUARDANDO_APROVACAO, null);

        assertThat(chamado.getStatus()).isEqualTo(StatusChamado.AGUARDANDO_APROVACAO);
        assertThat(chamado.getResolucao()).isEqualTo("Em análise pelo agente.");
    }
}
