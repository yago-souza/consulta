package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.model.Consulta;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AgendamentoRequestDTO {
    private Long pacienteId;
    private Long medicoId;
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

