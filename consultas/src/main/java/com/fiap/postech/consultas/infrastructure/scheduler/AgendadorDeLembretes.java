package com.fiap.postech.consultas.infrastructure.scheduler;

import com.fiap.postech.consultas.application.services.NotificacaoService;
import com.fiap.postech.consultas.domain.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class AgendadorDeLembretes {

    private final ConsultaRepository consultaRepository;
    private final NotificacaoService notificacaoService;

    public AgendadorDeLembretes(ConsultaRepository consultaRepository, NotificacaoService notificacaoService) {
        this.consultaRepository = consultaRepository;
        this.notificacaoService = notificacaoService;
    }

    @Scheduled(cron = "0 * * * * *") // a cada minuto
    public void enviarLembretes() {
        LocalDateTime agora = LocalDateTime.now();
        LocalDateTime umaHoraDepois = agora.plusHours(1);

        List<Consulta> consultas = consultaRepository.buscarConsultasEntre(agora, umaHoraDepois);

        for (Consulta consulta : consultas) {
            notificacaoService.enviarLembreteConsulta(consulta);
        }
    }
}
