package com.fiap.postech.consultas.infrastructure.scheduler;

import com.fiap.postech.consultas.application.usecases.BuscarConsultasPorPeriodoUseCase;
import com.fiap.postech.consultas.application.usecases.EnviarLembretesUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Component
public class AgendadorDeLembretes {

    private final BuscarConsultasPorPeriodoUseCase buscarConsultasPorPeriodo;
    private final EnviarLembretesUseCase enviarLembrete;

    public AgendadorDeLembretes(BuscarConsultasPorPeriodoUseCase buscarConsultasPorPeriodo, EnviarLembretesUseCase enviarLembrete) {
        this.buscarConsultasPorPeriodo = buscarConsultasPorPeriodo;
        this.enviarLembrete = enviarLembrete;
    }

    // Lembrete de manhã
    @Scheduled(cron = "0 0 7 * * *") // todos os dias às 07:00
    public void enviarLembretesDoDia() {
        LocalDate hoje = LocalDate.now();
        List<Consulta> consultas = buscarConsultasPorPeriodo.executar(
                hoje.atStartOfDay(), hoje.plusDays(1).atStartOfDay().minusSeconds(1));
        enviarLembrete.executar(consultas);
    }

    // Lembrete 1 hora antes
    @Scheduled(cron = "0 * * * * *") // a cada minuto
    public void enviarLembretesProximos() {
        LocalDateTime agora = LocalDateTime.now();
        List<Consulta> consultas = buscarConsultasPorPeriodo.executar(agora, agora.plusMinutes(1));
        enviarLembrete.executar(consultas);
    }
}

