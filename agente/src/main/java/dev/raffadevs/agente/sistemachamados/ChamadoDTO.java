package dev.raffadevs.agente.sistemachamados;

public record ChamadoDTO(
        Long id,
        Long solicitanteId,
        String titulo,
        String descricao,
        String categoria,
        String prioridade,
        String status,
        String resolucao) {
}
