package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import com.fiap.postech.consultas.domain.exception.ConsultaNaoEncontradaException;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CancelaConsultaUseCase {

    private final ConsultaRepository consultaRepository;

    public CancelaConsultaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta executar(UUID consultaId) {
        Consulta consulta = consultaRepository.buscarPorId(consultaId)
                .orElseThrow(() -> new ConsultaNaoEncontradaException("Consulta não encontrada com o ID: " + consultaId));

        if (consulta.getStatus() == StatusConsulta.CANCELADA) {
            throw new IllegalStateException("Consulta já está cancelada.");
        }

        consulta.setStatus(StatusConsulta.CANCELADA);
        consultaRepository.salvar(consulta);
        return consulta;
    }
}