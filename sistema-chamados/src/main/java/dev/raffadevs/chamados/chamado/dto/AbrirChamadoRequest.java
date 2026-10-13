package dev.raffadevs.chamados.chamado.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AbrirChamadoRequest(
        @NotNull Long solicitanteId,
        @NotBlank String titulo,
        @NotBlank String descricao,
        @NotBlank String categoria,
        String prioridade) {
}
