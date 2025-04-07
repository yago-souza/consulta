package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class BuscarConsultasProximasUseCase {
    private final ConsultaRepository consultaRepository;

    public BuscarConsultasProximasUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> executar() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime daquiUmaHora = agora.plusHours(1);
        return consultaRepository.buscarConsultasEntre(daquiUmaHora.minusMinutes(1), daquiUmaHora.plusMinutes(1));
    }
}