package dev.raffadevs.agente.execucao;

import static org.assertj.core.api.Assertions.assertThat;

import dev.raffadevs.agente.IntegrationTestBase;
import dev.raffadevs.agente.execucao.dto.ExecucaoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

class ExecucaoControllerIT extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ExecucaoAgenteRepository execucaoAgenteRepository;

    @Autowired
    private PassoAgenteRepository passoAgenteRepository;

    @Test
    void listaExecucoesComSeusPassosParaUmChamado() {
        ExecucaoAgente execucao = execucaoAgenteRepository.save(new ExecucaoAgente(5L));
        execucao.concluir("Conta desbloqueada após confirmação de identidade.");
        execucaoAgenteRepository.save(execucao);
        passoAgenteRepository.save(new PassoAgente(execucao.getId(), 1, "consultarUsuario", "{\"usuarioId\":9}", "{\"contaBloqueada\":true}"));
        passoAgenteRepository.save(new PassoAgente(execucao.getId(), 2, "desbloquearConta", "{\"usuarioId\":9}", "{\"aguardandoAprovacao\":true}"));

        ExecucaoResponse[] execucoes = restTemplate.getForObject("/api/chamados/5/execucoes", ExecucaoResponse[].class);

        assertThat(execucoes).hasSize(1);
        assertThat(execucoes[0].status()).isEqualTo("CONCLUIDA");
        assertThat(execucoes[0].passos()).hasSize(2);
        assertThat(execucoes[0].passos().get(1).ferramenta()).isEqualTo("desbloquearConta");
    }
}
