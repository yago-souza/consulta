package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.PacienteNaoEncontradoException;
import com.fiap.postech.consultas.interfaces.dtos.PacienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteClient {

    private final RestTemplate restTemplate;
    private final String pacienteApiUrl;

    @Autowired
    public PacienteClient(RestTemplate restTemplate,
                          @Value("${patients-api.url}") String pacienteApiUrl) {
        this.restTemplate = restTemplate;
        this.pacienteApiUrl = pacienteApiUrl;
    }

    public void validarPacienteExistente(Long pacienteId) {
        try {
            restTemplate.getForEntity(pacienteApiUrl + "/api/patients-system/v1/patients/" + pacienteId, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }

    public PacienteResponseDTO buscarPaciente(Long pacienteId) {
        try {
            ResponseEntity<PacienteResponseDTO> response = restTemplate.getForEntity(
                    pacienteApiUrl + "/api/patients-system/v1/patients/" + pacienteId,
                    PacienteResponseDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }
}




