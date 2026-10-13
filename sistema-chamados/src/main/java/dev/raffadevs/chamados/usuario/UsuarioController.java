package dev.raffadevs.chamados.usuario;

import dev.raffadevs.chamados.usuario.dto.UsuarioResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/{id}")
    public UsuarioResponse buscar(@PathVariable Long id) {
        return UsuarioResponse.from(usuarioService.buscarOuFalhar(id));
    }

    @PostMapping("/{id}/desbloquear")
    public UsuarioResponse desbloquear(@PathVariable Long id) {
        return UsuarioResponse.from(usuarioService.desbloquear(id));
    }
}
