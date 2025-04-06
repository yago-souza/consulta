package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.application.services.NotificacaoService;
import com.fiap.postech.consultas.domain.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;

import java.time.LocalDateTime;
import java.util.List;

public class EnviarLembretesUseCase {
    private final ConsultaRepository consultaRepository;
    private final NotificacaoService notificacaoService;

    public EnviarLembretesUseCase(ConsultaRepository repo, NotificacaoService notificacao) {
        this.consultaRepository = repo;
        this.notificacaoService = notificacao;
    }

    public void executar() {
        LocalDateTime agora = LocalDateTime.now();
        List<Consulta> consultas = consultaRepository.buscarConsultasParaHorario(agora.plusHours(1));
        consultas.forEach(c -> notificacaoService.enviarLembrete(c));
    }
}
