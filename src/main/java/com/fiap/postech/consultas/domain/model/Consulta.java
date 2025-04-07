package com.fiap.postech.consultas.domain.model;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
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
