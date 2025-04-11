package com.fiap.postech.consultas.application.usecases;

import com.fiap.postech.consultas.application.services.NotificacaoService;
import com.fiap.postech.consultas.domain.model.Consulta;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnviarLembretesUseCase {
    private final NotificacaoService notificacaoService;

    public EnviarLembretesUseCase(NotificacaoService notificacaoService) {
        this.notificacaoService = notificacaoService;
    }

    public void executar(List<Consulta> consultas) {
        if (consultas == null) {
            return;
        }
        consultas.forEach(notificacaoService::enviarLembreteConsulta);
    }
}
