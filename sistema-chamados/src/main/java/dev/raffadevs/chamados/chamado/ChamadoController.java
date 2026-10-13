package dev.raffadevs.chamados.chamado;

import dev.raffadevs.chamados.chamado.dto.AbrirChamadoRequest;
import dev.raffadevs.chamados.chamado.dto.AtualizarChamadoRequest;
import dev.raffadevs.chamados.chamado.dto.ChamadoResponse;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chamados")
public class ChamadoController {

    private final ChamadoService chamadoService;
    private final ChamadoRepository chamadoRepository;

    public ChamadoController(ChamadoService chamadoService, ChamadoRepository chamadoRepository) {
        this.chamadoService = chamadoService;
        this.chamadoRepository = chamadoRepository;
    }

    @PostMapping
    public ResponseEntity<ChamadoResponse> abrir(@Valid @RequestBody AbrirChamadoRequest request) {
        Prioridade prioridade = request.prioridade() == null
                ? Prioridade.MEDIA
                : Prioridade.valueOf(request.prioridade().toUpperCase());
        Chamado chamado = chamadoService.abrir(
                request.solicitanteId(), request.titulo(), request.descricao(), request.categoria(), prioridade);
        return ResponseEntity.status(HttpStatus.CREATED).body(ChamadoResponse.from(chamado));
    }

    @GetMapping
    public List<ChamadoResponse> listar() {
        return chamadoRepository.findAll().stream().map(ChamadoResponse::from).toList();
    }

    @GetMapping("/{id}")
    public ChamadoResponse buscar(@PathVariable Long id) {
        return ChamadoResponse.from(chamadoService.buscarOuFalhar(id));
    }

    @PatchMapping("/{id}")
    public ChamadoResponse atualizar(@PathVariable Long id, @Valid @RequestBody AtualizarChamadoRequest request) {
        StatusChamado novoStatus = StatusChamado.valueOf(request.status().toUpperCase());
        return ChamadoResponse.from(chamadoService.atualizar(id, novoStatus, request.resolucao()));
    }
}
