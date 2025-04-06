package com.fiap.postech.consultas.infrastructure.persistence;

import com.fiap.postech.consultas.domain.Consulta;
import com.fiap.postech.consultas.infrastructure.PacienteClient;
import com.fiap.postech.consultas.interfaces.dtos.Paciente;

import java.time.format.DateTimeFormatter;

public class NotificacaoServiceImpl {

    private final PacienteClient pacienteClient;

    public NotificacaoServiceImpl(PacienteClient pacienteClient) {
        this.pacienteClient = pacienteClient;
    }

    public void enviarLembreteConsulta(Consulta consulta) {
        Paciente paciente = pacienteClient.buscarPorId(consulta.getPacienteId());

        String email = paciente.getEmail();
        String mensagem = String.format("Olá %s, você tem uma consulta marcada para %s.",
                paciente.getNome(),
                consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        // Simulação de envio
        System.out.println("Enviando e-mail para " + email + ": " + mensagem);

        // Aqui entraria integração com serviço real de envio de e-mail ou outro canal.
    }
}


