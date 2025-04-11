package com.fiap.postech.consultas.domain.exception;

public class ConsultaJaExisteException extends RuntimeException{
    public ConsultaJaExisteException (String message) {
        super(message);
    }
}
