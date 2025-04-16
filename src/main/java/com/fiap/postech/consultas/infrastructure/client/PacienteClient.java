package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.PacienteNaoEncontradoException;
import com.fiap.postech.consultas.interfaces.dtos.PacienteResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
public class PacienteClient {

    private final RestTemplate restTemplate;
    private final String pacienteApiUrl;
    private final AuthClient authClient;

    @Autowired
    public PacienteClient(RestTemplate restTemplate,
                          @Value("${patients-api.url}") String pacienteApiUrl,
                          AuthClient authClient) {
        this.restTemplate = restTemplate;
        this.pacienteApiUrl = pacienteApiUrl;
        this.authClient = authClient;
    }

    public void validarPacienteExistente(Long pacienteId) {
        String token = authClient.obterToken("app_patient", "Caf3c0mL3it3");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            restTemplate.exchange(
                    pacienteApiUrl + "/api/patients-system/v1/patients/" + pacienteId,
                    HttpMethod.GET,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }

    public PacienteResponseDTO buscarPaciente(Long pacienteId) {
        String token = authClient.obterToken("app_patient", "Caf3c0mL3it3");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        try {
            ResponseEntity<PacienteResponseDTO> response = restTemplate.exchange(
                    pacienteApiUrl + "/api/patients-system/v1/patients/" + pacienteId,
                    HttpMethod.GET,
                    entity,
                    PacienteResponseDTO.class
            );
            return response.getBody();
        } catch (HttpClientErrorException.NotFound e) {
            throw new PacienteNaoEncontradoException("Paciente com ID " + pacienteId + " não encontrado.");
        }
    }
}





