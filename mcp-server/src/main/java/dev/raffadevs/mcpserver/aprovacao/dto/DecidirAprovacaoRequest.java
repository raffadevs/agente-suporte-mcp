package dev.raffadevs.mcpserver.aprovacao.dto;

import jakarta.validation.constraints.NotBlank;

public record DecidirAprovacaoRequest(@NotBlank String decisao, @NotBlank String decididoPor) {
}
