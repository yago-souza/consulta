package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BuscarConsultasPorPeriodoUseCase {

    private final ConsultaRepository consultaRepository;

    public BuscarConsultasPorPeriodoUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> executar(LocalDateTime inicio, LocalDateTime fim) {
        return consultaRepository.buscarConsultasEntre(inicio, fim);
    }
}
