package com.fiap.postech.consultas.infrastructure.persistence;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.client.PacienteClient;
import com.fiap.postech.consultas.infrastructure.PacienteResponse;
import org.slf4j.LoggerFactory;

import java.time.format.DateTimeFormatter;

public class NotificacaoServiceImpl {
    private static final org.slf4j.Logger logger = LoggerFactory.getLogger(NotificacaoServiceImpl.class);
    private final PacienteClient pacienteClient;

    public NotificacaoServiceImpl(PacienteClient pacienteClient) {
        this.pacienteClient = pacienteClient;
    }

    public void enviarLembreteConsulta(Consulta consulta) {
        PacienteResponse paciente = pacienteClient.buscarPaciente(consulta.getPacienteId());

        String email = paciente.getEmail();
        String mensagem = String.format("Olá %s, você tem uma consulta marcada para %s.",
                paciente.getNome(),
                consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Simulação de envio
        logger.info("Enviando e-mail para {}: {} ", email, mensagem);

        // TODO Aqui entraria integração com serviço real de envio de e-mail ou outro canal.
    }
}


