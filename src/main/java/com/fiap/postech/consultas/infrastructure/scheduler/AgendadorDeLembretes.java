package com.fiap.postech.consultas.infrastructure.scheduler;

import com.fiap.postech.consultas.application.usecases.BuscarConsultasDoDiaUseCase;
import com.fiap.postech.consultas.application.usecases.BuscarConsultasProximasUseCase;
import com.fiap.postech.consultas.application.usecases.EnviarLembretesUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class AgendadorDeLembretes {

    private final BuscarConsultasDoDiaUseCase buscarConsultasDoDia;
    private final BuscarConsultasProximasUseCase buscarConsultasProximas;
    private final EnviarLembretesUseCase enviarLembrete;

    public AgendadorDeLembretes(
            BuscarConsultasDoDiaUseCase buscarConsultasDoDia,
            BuscarConsultasProximasUseCase buscarConsultasProximas,
            EnviarLembretesUseCase enviarLembrete) {
        this.buscarConsultasDoDia = buscarConsultasDoDia;
        this.buscarConsultasProximas = buscarConsultasProximas;
        this.enviarLembrete = enviarLembrete;
    }

    // Lembrete de manhã
    @Scheduled(cron = "0 0 7 * * *") // todos os dias às 07:00
    public void enviarLembretesDoDia() {
        List<Consulta> consultas = buscarConsultasDoDia.executar();
        enviarLembrete.executar(consultas);
    }

    // Lembrete 1 hora antes
    @Scheduled(cron = "0 * * * * *") // a cada minuto
    public void enviarLembretesProximos() {
        List<Consulta> consultas = buscarConsultasProximas.executar();
        enviarLembrete.executar(consultas);
    }
}

