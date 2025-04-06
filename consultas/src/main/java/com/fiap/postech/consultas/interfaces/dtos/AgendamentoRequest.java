package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.Consulta;
import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class AgendamentoRequest {
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDateTime horario;
    private String exame;
    private StatusConsulta especialidade;

    public Consulta toDomain() {
        return new Consulta(
                pacienteId,
                medicoId,
                horario,
                exame,
                especialidade
        );
    }
}

