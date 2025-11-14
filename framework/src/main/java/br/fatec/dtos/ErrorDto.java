package br.fatec.dtos;

import java.time.Instant;

public record ErrorDto(
    Instant timestamp,
    String path,
    int status,
    String message,
    String error
) {
}
