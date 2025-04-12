package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.PacienteNaoEncontradoException;
import com.fiap.postech.consultas.infrastructure.PacienteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class PacienteClient {

    private final RestTemplate restTemplate;

    public PacienteClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarPacienteExistente(UUID pacienteId) {
        try {
            restTemplate.getForEntity("http://api-pacientes/pacientes/" + pacienteId, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }

    public PacienteResponse buscarPaciente(UUID pacienteId) {
        try {
            ResponseEntity<PacienteResponse> response = restTemplate.getForEntity(
                    "http://api-pacientes/pacientes/" + pacienteId,
                    PacienteResponse.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }
}




