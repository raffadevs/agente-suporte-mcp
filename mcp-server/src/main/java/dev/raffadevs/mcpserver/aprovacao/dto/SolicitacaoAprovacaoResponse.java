package dev.raffadevs.mcpserver.aprovacao.dto;

import dev.raffadevs.mcpserver.aprovacao.SolicitacaoAprovacao;
import java.time.Instant;

public record SolicitacaoAprovacaoResponse(
        Long id,
        String ferramenta,
        Long chamadoId,
        String acaoProposta,
        String parametros,
        String status,
        boolean usada,
        String decididoPor,
        Instant decididoEm,
        Instant criadoEm) {

    public static SolicitacaoAprovacaoResponse from(SolicitacaoAprovacao solicitacao) {
        return new SolicitacaoAprovacaoResponse(
                solicitacao.getId(),
                solicitacao.getFerramenta(),
                solicitacao.getChamadoId(),
                solicitacao.getAcaoProposta(),
                solicitacao.getParametros(),
                solicitacao.getStatus().name(),
                solicitacao.isUsada(),
                solicitacao.getDecididoPor(),
                solicitacao.getDecididoEm(),
                solicitacao.getCriadoEm());
    }
}
