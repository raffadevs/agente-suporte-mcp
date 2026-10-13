package dev.raffadevs.agente.execucao.dto;

import dev.raffadevs.agente.execucao.ExecucaoAgente;
import java.time.Instant;
import java.util.List;

public record ExecucaoResponse(
        Long id,
        Long chamadoId,
        String status,
        String resultado,
        Instant iniciadaEm,
        Instant finalizadaEm,
        List<PassoResponse> passos) {

    public static ExecucaoResponse from(ExecucaoAgente execucao, List<PassoResponse> passos) {
        return new ExecucaoResponse(
                execucao.getId(), execucao.getChamadoId(), execucao.getStatus().name(), execucao.getResultado(),
                execucao.getIniciadaEm(), execucao.getFinalizadaEm(), passos);
    }
}
