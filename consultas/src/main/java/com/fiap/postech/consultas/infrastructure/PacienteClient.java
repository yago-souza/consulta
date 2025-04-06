package com.fiap.postech.consultas.infrastructure;

import com.fiap.postech.consultas.interfaces.dtos.Paciente;

import java.util.UUID;

public interface PacienteClient {
    Paciente buscarPorId(UUID pacienteId);
}

