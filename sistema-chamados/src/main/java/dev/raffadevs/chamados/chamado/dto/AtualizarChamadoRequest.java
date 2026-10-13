package dev.raffadevs.chamados.chamado.dto;

import jakarta.validation.constraints.NotBlank;

public record AtualizarChamadoRequest(@NotBlank String status, String resolucao) {
}
