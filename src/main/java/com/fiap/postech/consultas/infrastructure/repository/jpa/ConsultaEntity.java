package com.fiap.postech.consultas.infrastructure.repository.jpa;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    private UUID pacienteId;
    private UUID medicoId;
    private LocalDateTime dataHora;
    private String exame;
    private StatusConsulta status;
}