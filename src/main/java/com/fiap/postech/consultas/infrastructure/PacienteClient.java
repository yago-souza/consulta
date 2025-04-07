package com.fiap.postech.consultas.infrastructure;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Component
public class PacienteClient {
    private final RestTemplate restTemplate;

    public PacienteClient(RestTemplateBuilder builder) {
        this.restTemplate = builder.build();
    }

    public PacienteResponse buscarPaciente(UUID id) {
        return restTemplate.getForObject("http://url-da-api/pacientes/" + id, PacienteResponse.class);
    }
}


