package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.services.NotificacaoService;
import com.fiap.postech.consultas.application.usecases.EnviarLembretesUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;

class EnviarLembretesUseCaseTest {

    @Mock
    private NotificacaoService notificacaoService;

    @InjectMocks
    private EnviarLembretesUseCase enviarLembretesUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void executar_deveEnviarLembreteParaCadaConsulta() {
        // Arrange
        Consulta consulta1 = new Consulta();
        Consulta consulta2 = new Consulta();
        List<Consulta> consultas = Arrays.asList(consulta1, consulta2);

        // Act
        enviarLembretesUseCase.executar(consultas);

        // Assert
        verify(notificacaoService, times(2)).enviarLembreteConsulta(consulta1);
        verify(notificacaoService, times(2)).enviarLembreteConsulta(consulta2);
    }

    @Test
    void executar_naoDeveEnviarLembreteSeListaDeConsultasEstiverVazia() {
        // Arrange
        List<Consulta> consultas = Arrays.asList();

        // Act
        enviarLembretesUseCase.executar(consultas);

        // Assert
        verify(notificacaoService, never()).enviarLembreteConsulta(any(Consulta.class));
    }

    @Test
    void executar_deveLidarComListaDeConsultasNula() {
        // Arrange
        List<Consulta> consultas = null;

        // Act
        enviarLembretesUseCase.executar(consultas);

        // Assert
        verify(notificacaoService, never()).enviarLembreteConsulta(any(Consulta.class));
    }
}