package com.fiap.postech.consultas.infrastructure;

import lombok.Data;

import java.util.UUID;

@Data
public class PacienteResponse {
    private UUID id;
    private String nome;
    private String email;
}
