package com.fiap.postech.consultas.application.services;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.client.PacienteClient;
import com.fiap.postech.consultas.interfaces.dtos.PacienteResponseDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class NotificacaoServiceTest {

    @Mock
    private PacienteClient pacienteClient;

    @InjectMocks
    private NotificacaoService notificacaoService;

    @Test
    void deveEnviarLembreteConsultaComSucesso() {
        Long pacienteId = new Random().nextLong();
        LocalDateTime dataConsulta = LocalDateTime.of(2025, 4, 20, 14, 30);

        PacienteResponseDTO pacienteResponseDTO = new PacienteResponseDTO();
        pacienteResponseDTO.setId(pacienteId);
        pacienteResponseDTO.setFirstName("João");
        pacienteResponseDTO.setEmail("joao@email.com");

        Consulta consulta = new Consulta();
        consulta.setPacienteId(pacienteId);
        consulta.setDataHora(dataConsulta);

        when(pacienteClient.buscarPaciente(pacienteId)).thenReturn(pacienteResponseDTO);

        // Esse teste apenas verifica se não há exceções durante a execução
        assertDoesNotThrow(() -> notificacaoService.enviarLembreteConsulta(consulta));
    }
}

