package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.BuscarConsultasPorPeriodoUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class BuscarConsultasPorPeriodoUseCaseTest {

    private ConsultaRepository consultaRepository;
    private BuscarConsultasPorPeriodoUseCase useCase;

    @BeforeEach
    void setUp() {
        consultaRepository = mock(ConsultaRepository.class);
        useCase = new BuscarConsultasPorPeriodoUseCase(consultaRepository);
    }

    @Test
    void deveRetornarConsultasDentroDoPeriodo() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.of(2025, 4, 10, 10, 0);
        LocalDateTime fim = LocalDateTime.of(2025, 4, 10, 12, 0);

        Consulta consulta1 = new Consulta(new Random().nextLong(), new Random().nextLong(), inicio.plusMinutes(30), "Consulta 1");
        Consulta consulta2 = new Consulta(new Random().nextLong(), new Random().nextLong(), inicio.plusMinutes(90), "Consulta 2");
        List<Consulta> consultas = Arrays.asList(consulta1, consulta2);

        when(consultaRepository.buscarConsultasEntre(inicio, fim)).thenReturn(consultas);

        // Act
        List<Consulta> resultado = useCase.executar(inicio, fim);

        // Assert
        assertEquals(2, resultado.size());
        assertEquals(consulta1, resultado.get(0));
        assertEquals(consulta2, resultado.get(1));
        verify(consultaRepository).buscarConsultasEntre(inicio, fim);
    }

    @Test
    void deveRetornarListaVaziaQuandoNaoHouverConsultasNoPeriodo() {
        // Arrange
        LocalDateTime inicio = LocalDateTime.now();
        LocalDateTime fim = inicio.plusHours(1);

        when(consultaRepository.buscarConsultasEntre(inicio, fim)).thenReturn(List.of());

        // Act
        List<Consulta> resultado = useCase.executar(inicio, fim);

        // Assert
        assertEquals(0, resultado.size());
        verify(consultaRepository).buscarConsultasEntre(inicio, fim);
    }
}
