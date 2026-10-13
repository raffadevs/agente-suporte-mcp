package dev.raffadevs.mcpserver.ferramentas;

import dev.raffadevs.mcpserver.sistemachamados.ArtigoEncontradoDTO;
import dev.raffadevs.mcpserver.sistemachamados.ServicoDTO;
import dev.raffadevs.mcpserver.sistemachamados.SistemaChamadosClient;
import dev.raffadevs.mcpserver.sistemachamados.UsuarioDTO;
import java.util.List;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;

/**
 * Ferramentas de leitura: não alteram nenhum estado, então são executadas diretamente,
 * sem precisar de aprovação humana.
 */
@Service
public class FerramentasLeituraService {

    private final SistemaChamadosClient sistemaChamadosClient;

    public FerramentasLeituraService(SistemaChamadosClient sistemaChamadosClient) {
        this.sistemaChamadosClient = sistemaChamadosClient;
    }

    @Tool(name = "buscarArtigo", description =
            "Busca artigos da base de conhecimento mais relevantes para uma pergunta ou problema descrito pelo usuário.")
    public List<ArtigoEncontradoDTO> buscarArtigo(
            @ToolParam(description = "Pergunta ou descrição do problema, em linguagem natural") String pergunta) {
        return sistemaChamadosClient.buscarArtigos(pergunta);
    }

    @Tool(name = "consultarStatusServico", description =
            "Consulta o status atual de um serviço interno (por exemplo: sistema-ponto, vpn, email-corporativo).")
    public ServicoDTO consultarStatusServico(@ToolParam(description = "Nome do serviço") String nomeServico) {
        return sistemaChamadosClient.consultarStatusServico(nomeServico);
    }

    @Tool(name = "consultarUsuario", description =
            "Consulta os dados de um usuário pelo id, incluindo se a conta dele está bloqueada.")
    public UsuarioDTO consultarUsuario(@ToolParam(description = "Id do usuário") Long usuarioId) {
        return sistemaChamadosClient.buscarUsuario(usuarioId);
    }
}
