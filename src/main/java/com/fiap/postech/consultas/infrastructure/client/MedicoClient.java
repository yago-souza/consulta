package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.PacienteNaoEncontradoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class MedicoClient {

    private final RestTemplate restTemplate;
    private final String medicoApiUrl;
    private final AuthClient authClient;

    public MedicoClient(RestTemplate restTemplate,
                        @Value("${doctor-api.url}") String medicoApiUrl,
                        AuthClient authClient) {
        this.restTemplate = restTemplate;
        this.medicoApiUrl = medicoApiUrl;
        this.authClient = authClient;
    }

    public void validarMedicoExistente(Long medicoId) {
        String token = authClient.obterToken("app_doctor", "Ch4Gel4d0");

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token);

        HttpEntity<Void> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(
                medicoApiUrl + "/doctor-api/doctors/" + medicoId,
                HttpMethod.GET,
                entity,
                String.class
        );
        if (response.getStatusCode().is2xxSuccessful()) {
            String body = response.getBody();
            if (body != null && body.contains("\"Doctor not found\"")) {
                throw new PacienteNaoEncontradoException("Médico com ID " + medicoId + " não encontrado.");
            }
        }
    }
}


