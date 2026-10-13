package dev.raffadevs.mcpserver.aprovacao;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import dev.raffadevs.mcpserver.IntegrationTestBase;
import dev.raffadevs.mcpserver.aprovacao.dto.DecidirAprovacaoRequest;
import dev.raffadevs.mcpserver.aprovacao.dto.SolicitacaoAprovacaoResponse;
import dev.raffadevs.mcpserver.ferramentas.FerramentasEscritaService;
import dev.raffadevs.mcpserver.ferramentas.ResultadoFerramenta;
import dev.raffadevs.mcpserver.sistemachamados.UsuarioDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;

class AprovacaoFluxoIT extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private FerramentasEscritaService ferramentasEscritaService;

    @Test
    void chamadaDeFerramentaDeEscritaSemAprovacaoFicaPendenteEDepoisDeAprovadaExecutaDeVerdade() {
        when(sistemaChamadosClient.desbloquearUsuario(9L))
                .thenReturn(new UsuarioDTO(9L, "Ana Souza", "ana.souza@empresa.com", false));

        ResultadoFerramenta primeiraChamada = ferramentasEscritaService.desbloquearConta(1L, 9L, null);
        assertThat(primeiraChamada.aguardandoAprovacao()).isTrue();
        String aprovacaoId = primeiraChamada.aprovacaoId();

        SolicitacaoAprovacaoResponse[] pendentes = restTemplate.getForObject("/api/aprovacoes", SolicitacaoAprovacaoResponse[].class);
        assertThat(pendentes).anySatisfy(p -> assertThat(p.id()).isEqualTo(Long.valueOf(aprovacaoId)));

        DecidirAprovacaoRequest decisao = new DecidirAprovacaoRequest("APROVADA", "analista@empresa.com");
        SolicitacaoAprovacaoResponse decidida = restTemplate.postForObject(
                "/api/aprovacoes/" + aprovacaoId + "/decidir", decisao, SolicitacaoAprovacaoResponse.class);
        assertThat(decidida.status()).isEqualTo("APROVADA");
        verify(agenteClient).notificarDecisao(1L, Long.valueOf(aprovacaoId), "APROVADA");

        ResultadoFerramenta segundaChamada = ferramentasEscritaService.desbloquearConta(1L, 9L, aprovacaoId);
        assertThat(segundaChamada.sucesso()).isTrue();

        ResultadoFerramenta terceiraChamada = ferramentasEscritaService.desbloquearConta(1L, 9L, aprovacaoId);
        assertThat(terceiraChamada.sucesso()).isFalse();
    }
}
