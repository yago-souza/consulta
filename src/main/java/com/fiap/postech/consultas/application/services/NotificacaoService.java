package com.fiap.postech.consultas.application.services;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.client.PacienteClient;
import com.fiap.postech.consultas.interfaces.dtos.PacienteResponseDTO;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;

@Slf4j
@Service
public class NotificacaoService {
    private final PacienteClient pacienteClient;

    public static final String ACCOUNT_SID = System.getenv("TWILIO_ACCOUNT_SID");
    public static final String AUTH_TOKEN = System.getenv("TWILIO_AUTH_TOKEN");

    public NotificacaoService(PacienteClient pacienteClient) {
        this.pacienteClient = pacienteClient;
    }

    public void enviarLembreteConsulta(Consulta consulta) {
        PacienteResponseDTO paciente = pacienteClient.buscarPaciente(consulta.getPacienteId());

        String mensagem = String.format("Olá %s, você tem uma consulta marcada para %s.",
                paciente.getFirstName(),
                consulta.getDataHora().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")));

        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("whatsapp:" + paciente.getPhone()),
                        new com.twilio.type.PhoneNumber("whatsapp:+14155238886"),
                        mensagem)
                .create();
    }
}
