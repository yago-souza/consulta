package com.fiap.postech.consultas.infrastructure;

import lombok.Data;

@Data
public class PacienteResponse {
    private Long id;
    private String nome;
    private String email;
}
