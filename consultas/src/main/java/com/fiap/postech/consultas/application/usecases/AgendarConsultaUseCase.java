package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.domain.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;

public class AgendarConsultaUseCase {
    private final ConsultaRepository consultaRepository;

    public AgendarConsultaUseCase(ConsultaRepository repo) {
        this.consultaRepository = repo;
    }

    public void executar(Consulta consulta) {
        // TODO validacoes (ex: conflito de horário, data no futuro)
        consultaRepository.salvar(consulta);
    }
}
