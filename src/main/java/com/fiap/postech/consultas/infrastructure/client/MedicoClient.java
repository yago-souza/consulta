package com.fiap.postech.consultas.infrastructure.client;

import com.fiap.postech.consultas.domain.exception.MedicoNaoEncontradoException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class MedicoClient {

    private final RestTemplate restTemplate;

    public MedicoClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validarMedicoExistente(UUID medicoId) {
        try {
            restTemplate.getForEntity("http://api-medicos/medicos/" + medicoId, Void.class);
        } catch (HttpClientErrorException.NotFound e) {
            throw new MedicoNaoEncontradoException("Médico com ID " + medicoId + " não encontrado.");
        }
    }
}

