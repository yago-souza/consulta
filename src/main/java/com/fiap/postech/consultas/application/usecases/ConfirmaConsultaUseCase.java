package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConsultaInvalidaException;
import com.fiap.postech.consultas.domain.exception.ConsultaNaoEncontradaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

@Service
public class ConfirmaConsultaUseCase {

    private final ConsultaRepository consultaRepository;

    public ConfirmaConsultaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta executar(Long consultaId) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada com o ID: " + consultaId));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new ConsultaInvalidaException("A consulta está cancelada e não pode ser confirmada.");
        }
        if (consulta.getStatus() != StatusConsulta.AGENDADA) {
            throw new IllegalStateException("A consulta só pode ser confirmada se estiver com o status AGENDADA.");
        }

        consulta.setStatus(StatusConsulta.CONFIRMADA);
        consultaRepository.salvar(consulta);

        return consulta;
    }
}