package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ConsultaResponseDTO {
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHora;
    private String exame;
    private StatusConsulta status;
}

