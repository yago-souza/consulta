package com.fiap.postech.consultas.domain.model;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class Consulta {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHora;
    private String exame;
    private StatusConsulta status;

    public Consulta(Long pacienteId, Long medicoId, LocalDateTime dataHora, String exame) {
        this.pacienteId = pacienteId;
        this.medicoId = medicoId;
        this.dataHora = dataHora;
        this.exame = exame;
    }

}
