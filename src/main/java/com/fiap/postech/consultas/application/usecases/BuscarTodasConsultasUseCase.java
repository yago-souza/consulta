package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BuscarTodasConsultasUseCase {

    private final ConsultaRepository consultaRepository;

    public BuscarTodasConsultasUseCase(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public List<Consulta> executar() {
        return consultaRepository.buscarTodas();
    }
}
