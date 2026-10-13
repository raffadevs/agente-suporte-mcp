package dev.raffadevs.agente.execucao.dto;

import dev.raffadevs.agente.execucao.PassoAgente;
import java.time.Instant;

public record PassoResponse(
        Long id, int ordem, String ferramenta, String entrada, String saida, String raciocinio, Instant criadoEm) {

    public static PassoResponse from(PassoAgente passo) {
        return new PassoResponse(
                passo.getId(), passo.getOrdem(), passo.getFerramenta(), passo.getEntrada(), passo.getSaida(),
                passo.getRaciocinio(), passo.getCriadoEm());
    }
}
