package dev.raffadevs.mcpserver.sistemachamados;

public record AbrirChamadoRequest(Long solicitanteId, String titulo, String descricao, String categoria, String prioridade) {
}
