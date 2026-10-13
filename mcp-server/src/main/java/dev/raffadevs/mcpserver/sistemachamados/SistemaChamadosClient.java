package dev.raffadevs.mcpserver.sistemachamados;

import java.util.List;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class SistemaChamadosClient {

    private final RestClient restClient;

    public SistemaChamadosClient(@Qualifier("sistemaChamadosRestClient") RestClient restClient) {
        this.restClient = restClient;
    }

    public UsuarioDTO buscarUsuario(Long usuarioId) {
        return restClient.get().uri("/api/usuarios/{id}", usuarioId).retrieve().body(UsuarioDTO.class);
    }

    public UsuarioDTO desbloquearUsuario(Long usuarioId) {
        return restClient.post().uri("/api/usuarios/{id}/desbloquear", usuarioId).retrieve().body(UsuarioDTO.class);
    }

    public ServicoDTO consultarStatusServico(String nomeServico) {
        return restClient.get().uri("/api/servicos/{nome}", nomeServico).retrieve().body(ServicoDTO.class);
    }

    public List<ArtigoEncontradoDTO> buscarArtigos(String pergunta) {
        return restClient.get().uri("/api/artigos/busca?q={q}", pergunta)
                .retrieve()
                .body(new ParameterizedTypeReference<List<ArtigoEncontradoDTO>>() {});
    }

    public ChamadoDTO abrirChamado(Long solicitanteId, String titulo, String descricao, String categoria, String prioridade) {
        return restClient.post().uri("/api/chamados")
                .body(new AbrirChamadoRequest(solicitanteId, titulo, descricao, categoria, prioridade))
                .retrieve().body(ChamadoDTO.class);
    }

    public ChamadoDTO atualizarChamado(Long chamadoId, String novoStatus, String resolucao) {
        return restClient.patch().uri("/api/chamados/{id}", chamadoId)
                .body(new AtualizarChamadoRequest(novoStatus, resolucao))
                .retrieve().body(ChamadoDTO.class);
    }
}
