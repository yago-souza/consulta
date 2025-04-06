package com.fiap.postech.consultas.domain;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
public class Consulta {
    private UUID id;
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDateTime dataHora;
    private String exame;
    private StatusConsulta status;

    public Consulta(UUID pacienteId, UUID medicoId, LocalDateTime dataHora, String exame, StatusConsulta status) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.dataHora = dataHora;
        this.exame = exame;
        this.status = status;
    }
}
