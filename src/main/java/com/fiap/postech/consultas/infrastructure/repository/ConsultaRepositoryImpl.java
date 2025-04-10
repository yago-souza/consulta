package com.fiap.postech.consultas.infrastructure.repository;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import com.fiap.postech.consultas.infrastructure.repository.jpa.ConsultaEntity;
import com.fiap.postech.consultas.infrastructure.repository.jpa.JpaConsultaRepository;
import com.fiap.postech.consultas.infrastructure.repository.mapper.ConsultaMapper;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class ConsultaRepositoryImpl implements ConsultaRepository {

    private final JpaConsultaRepository jpaRepository;

    public ConsultaRepositoryImpl(JpaConsultaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public void salvar(Consulta consulta) {
        ConsultaEntity entity = ConsultaMapper.toEntity(consulta);
        jpaRepository.save(entity);
    }

    @Override
    public List<Consulta> buscarConsultasParaDia(LocalDate data) {
        LocalDateTime inicioDoDia = data.atStartOfDay();
        LocalDateTime fimDoDia = data.atTime(23, 59, 59);
        return jpaRepository.findByDataHoraBetween(inicioDoDia, fimDoDia)
                .stream()
                .map(ConsultaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Consulta> buscarConsultasParaHorario(LocalDateTime horario) {
        return jpaRepository.findByDataHora(horario)
                .stream()
                .map(ConsultaMapper::toDomain)
                .toList();
    }

    @Override
    public List<Consulta> buscarConsultasEntre(LocalDateTime inicio, LocalDateTime fim) {
        return jpaRepository.findByDataHoraBetween(inicio, fim)
                .stream()
                .map(ConsultaMapper::toDomain)
                .toList();
    }

    @Override
    public Optional<Consulta> buscarPorId(UUID consultaId) {
        return jpaRepository.findById(consultaId)
                .map(ConsultaMapper::toDomain);
    }
}
