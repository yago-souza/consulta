package com.fiap.postech.consultas.application.services;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.PacienteClient;
import com.fiap.postech.consultas.infrastructure.PacienteResponse;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Service
public class NotificacaoService {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NotificacaoService.class);
    private final PacienteClient pacienteClient;

    public NotificacaoService(PacienteClient pacienteClient) {
        this.pacienteClient = pacienteClient;
    }

    public void enviarLembreteConsulta(Consulta consulta) {
        PacienteResponse paciente = pacienteClient.buscarPaciente(consulta.getPacienteId());

        String email = paciente.getEmail();
        String mensagem = String.format("Olá %s, você tem uma consulta marcada para %s.",
                paciente.getNome(),
                consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Simulação de envio
        logger.info("Enviando e-mail para {}: {}", email, mensagem);

        // Aqui entraria integração com serviço real de envio de e-mail ou outro canal.
    }
}
