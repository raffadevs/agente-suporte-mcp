package dev.raffadevs.chamados.chamado;

import static org.assertj.core.api.Assertions.assertThat;

import dev.raffadevs.chamados.IntegrationTestBase;
import dev.raffadevs.chamados.chamado.dto.AbrirChamadoRequest;
import dev.raffadevs.chamados.chamado.dto.AtualizarChamadoRequest;
import dev.raffadevs.chamados.chamado.dto.ChamadoResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ChamadoFluxoIT extends IntegrationTestBase {

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void abreEAtualizaChamado() {
        AbrirChamadoRequest novoChamado =
                new AbrirChamadoRequest(2L, "VPN não conecta", "A VPN pede senha e nunca autentica.", "acesso", "ALTA");

        ResponseEntity<ChamadoResponse> criado = restTemplate.postForEntity("/api/chamados", novoChamado, ChamadoResponse.class);

        assertThat(criado.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        Long chamadoId = criado.getBody().id();
        assertThat(criado.getBody().status()).isEqualTo("ABERTO");

        AtualizarChamadoRequest atualizacao = new AtualizarChamadoRequest("RESOLVIDO", "Senha da VPN resetada.");
        ResponseEntity<ChamadoResponse> atualizado = restTemplate.exchange(
                "/api/chamados/" + chamadoId,
                HttpMethod.PATCH,
                new HttpEntity<>(atualizacao),
                ChamadoResponse.class);

        assertThat(atualizado.getBody().status()).isEqualTo("RESOLVIDO");
        assertThat(atualizado.getBody().resolucao()).isEqualTo("Senha da VPN resetada.");
    }
}
