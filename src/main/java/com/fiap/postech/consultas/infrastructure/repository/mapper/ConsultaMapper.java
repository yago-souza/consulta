package com.fiap.postech.consultas.infrastructure.repository.mapper;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.repository.jpa.ConsultaEntity;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequestDTO;
import com.fiap.postech.consultas.interfaces.dtos.ConsultaResponseDTO;

public class ConsultaMapper {

    private ConsultaMapper() {
    }

    public static ConsultaEntity toEntity(Consulta consulta) {
        ConsultaEntity entity = new ConsultaEntity();
        entity.setId(consulta.getId());
        entity.setPacienteId(consulta.getPacienteId());
        entity.setMedicoId(consulta.getMedicoId());
        entity.setDataHora(consulta.getDataHora());
        entity.setExame(consulta.getExame());
        entity.setStatus(consulta.getStatus());
        return entity;
    }

    public static Consulta toDomain(ConsultaEntity entity) {
        Consulta consulta = new Consulta();
        consulta.setId(entity.getId());
        consulta.setPacienteId(entity.getPacienteId());
        consulta.setMedicoId(entity.getMedicoId());
        consulta.setDataHora(entity.getDataHora());
        consulta.setExame(entity.getExame());
        consulta.setStatus(entity.getStatus());
        return consulta;
    }

    public static ConsultaResponseDTO toResponse(Consulta consulta) {
        return new ConsultaResponseDTO(
                consulta.getId(),
                consulta.getPacienteId(),
                consulta.getMedicoId(),
                consulta.getDataHora(),
                consulta.getExame(),
                consulta.getStatus()
        );
    }

    public static Consulta toDomainFromDTO (AgendamentoRequestDTO dto) {
        Consulta consulta = new Consulta();
        consulta.setPacienteId(dto.getPacienteId());
        consulta.setMedicoId(dto.getMedicoId());
        consulta.setDataHora(dto.getHorario());
        consulta.setExame(dto.getExame());
        return consulta;
    }
}