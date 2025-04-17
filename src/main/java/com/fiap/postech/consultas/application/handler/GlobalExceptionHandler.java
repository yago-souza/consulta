package com.fiap.postech.consultas.application.handler;

import com.fiap.postech.consultas.application.dto.ErrorResponseDTO;
import com.fiap.postech.consultas.domain.exception.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConsultaJaExisteException.class)
    public ResponseEntity<ErrorResponseDTO> handleConsultaJaExiste(ConsultaJaExisteException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConsultaNaoEncontradaException.class)
    public ResponseEntity<ErrorResponseDTO> handleConsultaNaoEncontrada(ConsultaNaoEncontradaException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DataConsultaInvalidaException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataConsultaInvalida(DataConsultaInvalidaException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MedicoNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handleMedicoNaoEncontrado(MedicoNaoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PacienteNaoEncontradoException.class)
    public ResponseEntity<ErrorResponseDTO> handlePacienteNaoEncontrado(PacienteNaoEncontradoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ConflitoHorarioMedicoException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflitoMedico(ConflitoHorarioMedicoException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ConflitoHorarioPacienteException.class)
    public ResponseEntity<ErrorResponseDTO> handleConflitoPaciente(ConflitoHorarioPacienteException ex) {
        return buildErrorResponse(ex.getMessage(), HttpStatus.CONFLICT);
    }

    // Fallback para exceções não tratadas
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return buildErrorResponse("Erro interno do servidor. Tente novamente mais tarde.", HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private ResponseEntity<ErrorResponseDTO> buildErrorResponse(String message, HttpStatus status) {
        ErrorResponseDTO error = new ErrorResponseDTO(
                status.value(),
                status.getReasonPhrase(),
                message,
                LocalDateTime.now()
        );
        return new ResponseEntity<>(error, status);
    }
}

