package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioMedicoException;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioPacienteException;
import com.fiap.postech.consultas.domain.exception.DataConsultaInvalidaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import com.fiap.postech.consultas.infrastructure.client.MedicoClient;
import com.fiap.postech.consultas.infrastructure.client.PacienteClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AgendarConsultaUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @Mock
    private PacienteClient pacienteClient;

    @Mock
    private MedicoClient medicoClient;

    @InjectMocks
    private AgendarConsultaUseCase agendarConsultaUseCase;

    private Consulta consulta;

    @BeforeEach
    void setUp() {
        consulta = new Consulta();
        consulta.setId(new Random().nextLong());
        consulta.setMedicoId(new Random().nextLong());
        consulta.setPacienteId(new Random().nextLong());
        consulta.setDataHora(LocalDateTime.now().plusDays(1));
    }

    @Test
    void deveAgendarConsultaComSucesso() {
        when(consultaRepository.buscarConsultasParaHorario(any())).thenReturn(List.of());
        when(consultaRepository.salvar(any())).thenReturn(consulta);

        Consulta consultaAgendada = agendarConsultaUseCase.executar(consulta);

        assertNotNull(consultaAgendada);
        assertEquals(StatusConsulta.AGENDADA, consultaAgendada.getStatus());
    }

    @Test
    void deveLancarExcecaoParaDataNoPassado() {
        consulta.setDataHora(LocalDateTime.now().minusDays(1));
        assertThrows(DataConsultaInvalidaException.class, () -> agendarConsultaUseCase.executar(consulta));
    }

    @Test
    void deveLancarExcecaoParaConflitoComOutroHorarioDoMedico() {
        Consulta conflito = new Consulta();
        conflito.setMedicoId(consulta.getMedicoId());
        when(consultaRepository.buscarConsultasParaHorario(any())).thenReturn(List.of(conflito));

        assertThrows(ConflitoHorarioMedicoException.class, () -> agendarConsultaUseCase.executar(consulta));
    }

    @Test
    void deveLancarExcecaoParaConflitoComOutroHorarioDoPaciente() {
        Consulta conflito = new Consulta();
        conflito.setMedicoId(new Random().nextLong());
        conflito.setPacienteId(consulta.getPacienteId());
        when(consultaRepository.buscarConsultasParaHorario(any())).thenReturn(List.of(conflito));

        assertThrows(ConflitoHorarioPacienteException.class, () -> agendarConsultaUseCase.executar(consulta));
    }

}
