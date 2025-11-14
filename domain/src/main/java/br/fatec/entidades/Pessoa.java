package br.fatec.entidades;

import java.time.LocalDate;

public record Pessoa(
    Long id, // vai ser nulo no momento da criação
    String nome,
    LocalDate dataNascimento,
    boolean ativo
) {
}
