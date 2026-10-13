package dev.raffadevs.chamados.chamado.dto;

import dev.raffadevs.chamados.chamado.Chamado;
import java.time.Instant;

public record ChamadoResponse(
        Long id,
        Long solicitanteId,
        String titulo,
        String descricao,
        String categoria,
        String prioridade,
        String status,
        String resolucao,
        Instant criadoEm,
        Instant atualizadoEm) {

    public static ChamadoResponse from(Chamado chamado) {
        return new ChamadoResponse(
                chamado.getId(),
                chamado.getSolicitanteId(),
                chamado.getTitulo(),
                chamado.getDescricao(),
                chamado.getCategoria(),
                chamado.getPrioridade().name(),
                chamado.getStatus().name(),
                chamado.getResolucao(),
                chamado.getCriadoEm(),
                chamado.getAtualizadoEm());
    }
}
