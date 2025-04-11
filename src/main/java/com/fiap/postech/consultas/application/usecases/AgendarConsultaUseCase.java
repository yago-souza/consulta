package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioMedicoException;
import com.fiap.postech.consultas.domain.exception.ConflitoHorarioPacienteException;
import com.fiap.postech.consultas.domain.exception.ConsultaJaExisteException;
import com.fiap.postech.consultas.domain.exception.DataConsultaInvalidaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AgendarConsultaUseCase {

    private final ConsultaRepository consultaRepository;

    public AgendarConsultaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta executar(Consulta consulta) {
        validarDataFutura(consulta);
        validarConflitoDeHorarioMedico(consulta);
        validarConflitoDeHorarioPaciente(consulta);
        validarConsultaDuplicada(consulta);

        consulta.setStatus(StatusConsulta.AGENDADA);
        return consultaRepository.salvar(consulta);
    }

    private void validarDataFutura(Consulta consulta) {
        if (consulta.getDataHora().isBefore(LocalDateTime.now())) {
            throw new DataConsultaInvalidaException("A data e hora da consulta devem ser no futuro.");
        }
    }

    private void validarConflitoDeHorarioMedico(Consulta consulta) {
        List<Consulta> consultasNoHorario = consultaRepository.buscarConsultasParaHorario(consulta.getDataHora());
        boolean conflito = consultasNoHorario.stream()
                .anyMatch(c -> c.getMedicoId().equals(consulta.getMedicoId()));

        if (conflito) {
            throw new ConflitoHorarioMedicoException("O médico já possui uma consulta nesse horário.");
        }
    }

    private void validarConflitoDeHorarioPaciente(Consulta consulta) {
        List<Consulta> consultasNoHorario = consultaRepository.buscarConsultasParaHorario(consulta.getDataHora());
        boolean conflito = consultasNoHorario.stream()
                .anyMatch(c -> c.getPacienteId().equals(consulta.getPacienteId()));

        if (conflito) {
            throw new ConflitoHorarioPacienteException("O paciente já possui uma consulta nesse horário.");
        }
    }

    private void validarConsultaDuplicada(Consulta consulta) {
        List<Consulta> consultasNoHorario = consultaRepository.buscarConsultasParaHorario(consulta.getDataHora());
        boolean consultaDuplicada = consultasNoHorario.stream()
                .anyMatch(c ->
                        c.getMedicoId().equals(consulta.getMedicoId()) &&
                                c.getPacienteId().equals(consulta.getPacienteId()) &&
                                c.getDataHora().equals(consulta.getDataHora())
                );

        if (consultaDuplicada) {
            throw new ConsultaJaExisteException("Já existe uma consulta agendada com esses dados.");
        }
    }
}

