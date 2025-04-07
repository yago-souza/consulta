package com.fiap.postech.consultas.interfaces.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class Paciente {
    private UUID id;
    private String nome;
    private String email;
}

