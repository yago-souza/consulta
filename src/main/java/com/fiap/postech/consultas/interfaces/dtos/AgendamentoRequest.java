package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.model.Consulta;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AgendamentoRequest {
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDateTime horario;
    private String exame;

    public Consulta toDomain() {
        return new Consulta(
                pacienteId,
                medicoId,
                horario,
                exame
        );
    }
}

