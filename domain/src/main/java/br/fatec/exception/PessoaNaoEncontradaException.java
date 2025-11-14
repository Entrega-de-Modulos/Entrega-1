package br.fatec.exception;

public class PessoaNaoEncontradaException extends DomainException {
    public PessoaNaoEncontradaException(String message) {
        super(message);
    }
}