package com.fiap.postech.consultas.application.usecases;


import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;

import java.time.LocalDate;
import java.util.List;

public class BuscarConsultasDoDiaUseCase {
    private final ConsultaRepository consultaRepository;

    public BuscarConsultasDoDiaUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> executar() {
        return consultaRepository.buscarConsultasParaDia(LocalDate.now());
    }
}