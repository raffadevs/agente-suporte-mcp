package dev.raffadevs.mcpserver.agente;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

/**
 * Avisa o serviço de agente que uma aprovação foi decidida, para que ele retome o
 * raciocínio do chamado. Falhas aqui não devem impedir a decisão de ser registrada —
 * ficam logadas, e o retorno deste serviço nunca muda o resultado já persistido da aprovação.
 */
@Component
public class AgenteClient {

    private static final Logger log = LoggerFactory.getLogger(AgenteClient.class);

    private final RestClient restClient;

    public AgenteClient(@Qualifier("agenteRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public void notificarDecisao(Long chamadoId, Long aprovacaoId, String decisao) {
        if (chamadoId == null) {
            log.warn("Aprovação {} decidida sem chamadoId associado; agente não será notificado", aprovacaoId);
            return;
        }
        try {
            restClient.post()
                    .uri("/api/chamados/{chamadoId}/retomar?aprovacaoId={aprovacaoId}&decisao={decisao}",
                            chamadoId, aprovacaoId, decisao)
                    .retrieve()
                    .toBodilessEntity();
        } catch (Exception ex) {
            log.error("Falha ao notificar o agente sobre a decisão da aprovação {}: {}", aprovacaoId, ex.getMessage());
        }
    }
}
