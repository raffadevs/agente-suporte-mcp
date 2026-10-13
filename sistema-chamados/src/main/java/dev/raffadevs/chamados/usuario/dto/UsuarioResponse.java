package dev.raffadevs.chamados.usuario.dto;

import dev.raffadevs.chamados.usuario.Usuario;

public record UsuarioResponse(Long id, String nome, String email, boolean contaBloqueada) {
    public static UsuarioResponse from(Usuario usuario) {
        return new UsuarioResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.isContaBloqueada());
    }
}
