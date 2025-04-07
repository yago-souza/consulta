package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioMedicoException;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioPacienteException;
import com.fiap.postech.consultas.domain.exception.DataConsultaInvalidaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AgendarConsultaUseCaseTest {

    private ConsultaRepository consultaRepository;
    private AgendarConsultaUseCase useCase;

    @BeforeEach
    void setUp() {
        consultaRepository = mock(ConsultaRepository.class);
        useCase = new AgendarConsultaUseCase(consultaRepository);
    }

    @Test
    void deveAgendarConsultaComSucesso() {
        Consulta consulta = criarConsultaValida();

        when(consultaRepository.buscarConsultasParaHorario(consulta.getDataHora())).thenReturn(List.of());

        assertDoesNotThrow(() -> useCase.executar(consulta));
        verify(consultaRepository).salvar(consulta);
    }

    @Test
    void deveLancarExcecaoQuandoDataForPassada() {
        Consulta consulta = criarConsultaValida();
        consulta.setDataHora(LocalDateTime.now().minusHours(1));

        Exception exception = assertThrows(DataConsultaInvalidaException.class, () -> useCase.executar(consulta));
        assertEquals("A data e hora da consulta devem ser no futuro.", exception.getMessage());
    }

    @Test
    void deveLancarExcecaoQuandoMedicoTiverConflitoDeHorario() {
        Consulta novaConsulta = criarConsultaValida();

        // Simula consulta existente com mesmo médico e horário
        Consulta consultaExistente = new Consulta(
                UUID.randomUUID(), // paciente diferente
                novaConsulta.getMedicoId(), // mesmo médico
                novaConsulta.getDataHora(), "Outro exame", StatusConsulta.AGENDADA
        );

        when(consultaRepository.buscarConsultasParaHorario(novaConsulta.getDataHora()))
                .thenReturn(List.of(consultaExistente));

        Exception exception = assertThrows(ConflitoHorarioMedicoException.class, () -> useCase.executar(novaConsulta));
        assertEquals("O médico já possui uma consulta nesse horário.", exception.getMessage());
    }


    @Test
    void deveLancarExcecaoQuandoPacienteTiverConflitoDeHorario() {
        Consulta novaConsulta = criarConsultaValida();

        // Simula consulta existente com mesmo paciente e horário
        Consulta consultaExistente = new Consulta(
                novaConsulta.getPacienteId(), // mesmo paciente
                UUID.randomUUID(), // médico diferente
                novaConsulta.getDataHora(), "Outro exame", StatusConsulta.AGENDADA
        );
        when(consultaRepository.buscarConsultasParaHorario(novaConsulta.getDataHora()))
                .thenReturn(List.of(consultaExistente));

        Exception exception = assertThrows(ConflitoHorarioPacienteException.class, () -> useCase.executar(novaConsulta));
        assertEquals("O paciente já possui uma consulta nesse horário.", exception.getMessage());
    }

    private Consulta criarConsultaValida() {
        UUID pacienteId = UUID.randomUUID();
        UUID medicoId = UUID.randomUUID();
        LocalDateTime dataHora = LocalDateTime.now().plusDays(1);

        return new Consulta(pacienteId, medicoId, dataHora, "Consulta de rotina", StatusConsulta.AGENDADA);
    }
}