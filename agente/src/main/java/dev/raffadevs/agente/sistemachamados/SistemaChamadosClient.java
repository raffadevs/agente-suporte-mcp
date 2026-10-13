package dev.raffadevs.agente.sistemachamados;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SistemaChamadosClient {

    private final RestClient restClient;

    public SistemaChamadosClient(RestClient.Builder restClientBuilder, @Value("${app.sistema-chamados.base-url}") String baseUrl) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
    }

    public ChamadoDTO buscarChamado(Long chamadoId) {
        return restClient.get().uri("/api/chamados/{id}", chamadoId).retrieve().body(ChamadoDTO.class);
    }
}
