package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.ConfirmaConsultaUseCase;
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
class ConfirmaConsultaUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ConfirmaConsultaUseCase confirmaConsultaUseCase;

    @Test
    void deveConfirmarConsultaComSucesso() {
        // Arrange
        Long consultaId = new Random().nextLong();
        Consulta consulta = new Consulta();
        consulta.setId(consultaId);
        consulta.setStatus(StatusConsulta.AGENDADA);

        when(consultaRepository.buscarPorId(consultaId)).thenReturn(Optional.of(consulta));

        // Act
        Consulta resultado = confirmaConsultaUseCase.executar(consultaId);

        // Assert
        assertEquals(StatusConsulta.CONFIRMADA, resultado.getStatus());
        verify(consultaRepository).salvar(consulta);
    }

    @Test
    void deveLancarExcecaoQuandoConsultaNaoForEncontrada() {
        // Arrange
        Long consultaId = new Random().nextLong();
        when(consultaRepository.buscarPorId(consultaId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(ConsultaNaoEncontradaException.class, () -> confirmaConsultaUseCase.executar(consultaId));
    }
}
