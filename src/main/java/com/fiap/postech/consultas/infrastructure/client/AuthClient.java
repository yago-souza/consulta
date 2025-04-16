package com.fiap.postech.consultas.infrastructure.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthClient {

    private final RestTemplate restTemplate;
    private final String authUrl;

    public AuthClient(RestTemplate restTemplate, @Value("${auth-server.url}") String authUrl) {
        this.restTemplate = restTemplate;
        this.authUrl = authUrl;
    }

    public String obterToken(String clientId, String clientSecret) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> corpo = new HashMap<>();
        corpo.put("clientId", clientId);
        corpo.put("clientSecret", clientSecret);

        HttpEntity<Map<String, String>> requisicao = new HttpEntity<>(corpo, headers);

        ResponseEntity<Map> resposta = restTemplate.postForEntity(
                authUrl + "/auth/token", requisicao, Map.class);

        return (String) resposta.getBody().get("token"); // ou "access_token", depende da resposta
    }
}
