package com.fiap.postech.consultas.domain.exception;

public class MedicoNaoEncontradoException extends RuntimeException {
    public MedicoNaoEncontradoException(String message) {
        super(message);
    }
}
