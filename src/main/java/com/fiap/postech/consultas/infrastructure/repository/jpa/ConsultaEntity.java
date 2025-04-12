package com.fiap.postech.consultas.infrastructure.repository.jpa;

import com.fiap.postech.consultas.domain.enums.StatusConsulta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConsultaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long pacienteId;
    private Long medicoId;
    private LocalDateTime dataHora;
    private String exame;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusConsulta status;
}