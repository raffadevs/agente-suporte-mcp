package dev.raffadevs.chamados.servico.dto;

import dev.raffadevs.chamados.servico.Servico;

public record ServicoResponse(Long id, String nome, String status) {
    public static ServicoResponse from(Servico servico) {
        return new ServicoResponse(servico.getId(), servico.getNome(), servico.getStatus().name());
    }
}
