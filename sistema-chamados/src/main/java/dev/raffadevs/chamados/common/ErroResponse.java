package dev.raffadevs.chamados.common;

import java.time.Instant;

public record ErroResponse(Instant timestamp, int status, String erro, String detalhes) {
}
