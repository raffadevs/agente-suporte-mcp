package dev.raffadevs.agente.execucao;

import static org.assertj.core.api.Assertions.assertThat;

import dev.raffadevs.agente.sistemachamados.ChamadoDTO;
import org.junit.jupiter.api.Test;

class MensagensAgenteTest {

    @Test
    void mensagemInicialTrazTituloEDescricaoDoChamado() {
        ChamadoDTO chamado = new ChamadoDTO(1L, 2L, "VPN não conecta", "Senha nunca autentica", "acesso", "ALTA", "ABERTO", null);

        String mensagem = MensagensAgente.mensagemInicial(chamado);

        assertThat(mensagem).contains("VPN não conecta").contains("Senha nunca autentica").contains("#1");
    }

    @Test
    void mensagemRetomadaAprovadaInstruiAUsarOAprovacaoId() {
        String mensagem = MensagensAgente.mensagemRetomada(10L, 99L, "APROVADA");

        assertThat(mensagem).contains("APROVADA").contains("aprovacaoId=99").contains("#10");
    }

    @Test
    void mensagemRetomadaRejeitadaInstruiANaoExecutar() {
        String mensagem = MensagensAgente.mensagemRetomada(10L, 99L, "REJEITADA");

        assertThat(mensagem).contains("REJEITADA").contains("Não execute");
    }
}
