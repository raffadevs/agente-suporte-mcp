package dev.raffadevs.chamados.usuario;

import dev.raffadevs.chamados.common.RecursoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    @Transactional(readOnly = true)
    public Usuario buscarOuFalhar(Long id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new RecursoNaoEncontradoException("Usuário %d não encontrado".formatted(id)));
    }

    @Transactional
    public Usuario desbloquear(Long id) {
        Usuario usuario = buscarOuFalhar(id);
        usuario.desbloquear();
        return usuario;
    }
}
