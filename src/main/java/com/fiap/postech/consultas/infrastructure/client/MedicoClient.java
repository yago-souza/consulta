package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.MedicoNaoEncontradoException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

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

        try {
            restTemplate.exchange(
                    medicoApiUrl + medicoId,
                    HttpMethod.GET,
                    entity,
                    Void.class
            );
        } catch (HttpClientErrorException.NotFound e) {
            throw new MedicoNaoEncontradoException("Médico com ID " + medicoId + " não encontrado.");
        }
    }
}


