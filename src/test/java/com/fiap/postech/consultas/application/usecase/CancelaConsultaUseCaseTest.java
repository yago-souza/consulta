package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.CancelaConsultaUseCase;
import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConsultaNaoEncontradaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CancelaConsultaUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private CancelaConsultaUseCase cancelaConsultaUseCase;

    @Test
    void deveCancelarConsultaComSucesso() {
        // Arrange
        Long consultaId = new Random().nextLong();
        Consulta consulta = new Consulta();
        consulta.setId(consultaId);
        consulta.setStatus(StatusConsulta.AGENDADA);

        when(consultaRepository.buscarPorId(consultaId)).thenReturn(Optional.of(consulta));

        // Act
        Consulta resultado = cancelaConsultaUseCase.executar(consultaId);

        // Assert
        assertEquals(StatusConsulta.CANCELADA, resultado.getStatus());
        verify(consultaRepository).salvar(consulta);
    }

    @Test
    void deveLancarExcecaoSeConsultaNaoForEncontrada() {
        // Arrange
        Long consultaId = new Random().nextLong();
        when(consultaRepository.buscarPorId(consultaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ConsultaNaoEncontradaException.class, () -> cancelaConsultaUseCase.executar(consultaId));
    }
}
