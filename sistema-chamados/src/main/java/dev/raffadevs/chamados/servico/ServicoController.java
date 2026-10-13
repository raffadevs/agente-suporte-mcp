package dev.raffadevs.chamados.servico;

import dev.raffadevs.chamados.common.RecursoNaoEncontradoException;
import dev.raffadevs.chamados.servico.dto.ServicoResponse;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/servicos")
public class ServicoController {

    private final ServicoRepository servicoRepository;

    public ServicoController(ServicoRepository servicoRepository) {
        this.servicoRepository = servicoRepository;
    }

    @GetMapping("/{nome}")
    public ServicoResponse buscarPorNome(@PathVariable String nome) {
        return servicoRepository.findByNome(nome)
                .map(ServicoResponse::from)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Serviço '%s' não encontrado".formatted(nome)));
    }

    @GetMapping
    public List<ServicoResponse> listar() {
        return servicoRepository.findAll().stream().map(ServicoResponse::from).toList();
    }
}
