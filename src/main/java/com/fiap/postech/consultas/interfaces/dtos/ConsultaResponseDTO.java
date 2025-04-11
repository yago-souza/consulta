package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
public class ConsultaResponseDTO {
    private UUID id;
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDateTime dataHora;
    private String exame;
    private StatusConsulta status;
}

