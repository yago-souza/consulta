package com.fiap.postech.consultas.domain.exception;

public class ConsultaNaoEncontradaException extends RuntimeException {
    public ConsultaNaoEncontradaException(String mensagem) {
        super(mensagem);
    }
}
