package com.fiap.postech.consultas.domain.exception;

public class DataConsultaInvalidaException extends RuntimeException {
    public DataConsultaInvalidaException(String mensagem) {
        super(mensagem);
    }
}