package com.fiap.postech.consultas.interfaces.dtos;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;

import java.time.LocalDateTime;
import java.util.UUID;

public record ConsultaResponse(
        UUID id,
        UUID pacienteId,
        UUID medicoId,
        LocalDateTime dataHora,
        String descricao,
        StatusConsulta status
) {}
