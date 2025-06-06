package com.fiap.postech.consultas.infrastructure.repository.jpa;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface JpaConsultaRepository extends JpaRepository<ConsultaEntity, Long> {
    List<ConsultaEntity> findByDataHora(LocalDateTime dataHora);
    List<ConsultaEntity> findByDataHoraBetween(LocalDateTime inicio, LocalDateTime fim);

    @Override
    Optional<ConsultaEntity> findById(Long uuid);
}
