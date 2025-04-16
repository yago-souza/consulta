package com.fiap.postech.consultas.interfaces.dtos;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PacienteResponseDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String cpf;
    private LocalDate dateOfBirth;
    private String phone;
    private String number;
    private String street;
    private String neighborhood;
    private String city;
    private String state;
    private String zipcode;
}
