package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioMedicoException;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioPacienteException;
import com.fiap.postech.consultas.domain.exception.DataConsultaInvalidaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import com.fiap.postech.consultas.infrastructure.client.MedicoClient;
import com.fiap.postech.consultas.infrastructure.client.PacienteClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class AgendarConsultaUseCase {

    private final ConsultaRepository consultaRepository;
    private final PacienteClient pacienteClient;
    private final MedicoClient medicoClient;

    public AgendarConsultaUseCase(ConsultaRepository consultaRepository1, PacienteClient pacienteClient, MedicoClient medicoClient) {
        this.consultaRepository = consultaRepository1;
        this.pacienteClient = pacienteClient;
        this.medicoClient = medicoClient;
    }

    public Consulta executar(Consulta consulta) {
        validarExistenciaPacienteEMedico(consulta);
        validarDataFutura(consulta);
        validarConflitoDeHorarioMedico(consulta);
        validarConflitoDeHorarioPaciente(consulta);
        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.salvar(consulta);
    }

    private void validarExistenciaPacienteEMedico(Consulta consulta) {
        pacienteClient.validarPacienteExistente(consulta.getPacienteId());
        medicoClient.validarMedicoExistente(consulta.getMedicoId());
    }

    private void validarDataFutura(Consulta consulta) {
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new DataConsultaInvalidaException("A data e hora da consulta devem ser no futuro.");
        }
    }

    private void validarConflitoDeHorarioMedico(Consulta consulta) {
        LocalDateTime inicio = consulta.getDataHora();
        LocalDateTime fim = inicio.plusMinutes(45);

        List<Consulta> consultas = consultaRepository
                .buscarConsultasEntre(inicio.minusMinutes(45), fim); // Pega possíveis interseções

        boolean conflito = consultas.stream()
                .filter(c -> c.getMedicoId().equals(consulta.getMedicoId()))
                .anyMatch(c -> intervaloSobrepoe(c.getDataHora(), c.getDataHora().plusMinutes(45), inicio, fim));

        if (conflito) {
            throw new ConflitoHorarioMedicoException("O médico já possui uma consulta nesse intervalo.");
        }
    }

    private void validarConflitoDeHorarioPaciente(Consulta consulta) {
        LocalDateTime inicio = consulta.getDataHora();
        LocalDateTime fim = inicio.plusMinutes(45);

        List<Consulta> consultas = consultaRepository
                .buscarConsultasEntre(inicio.minusMinutes(45), fim); // Pega possíveis interseções

        boolean conflito = consultas.stream()
                .filter(c -> c.getPacienteId().equals(consulta.getPacienteId()))
                .anyMatch(c -> intervaloSobrepoe(c.getDataHora(), c.getDataHora().plusMinutes(45), inicio, fim));

        if (conflito) {
            throw new ConflitoHorarioPacienteException("O paciente já possui uma consulta nesse intervalo.");
        }
    }

    private boolean intervaloSobrepoe(LocalDateTime inicio1, LocalDateTime fim1, LocalDateTime inicio2, LocalDateTime fim2) {
        return !inicio1.isAfter(fim2) && !inicio2.isAfter(fim1);
    }
}

