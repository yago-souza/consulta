package com.fiap.postech.consultas.infrastructure.repository.mapper;

import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.repository.jpa.ConsultaEntity;
import com.fiap.postech.consultas.interfaces.dtos.ConsultaResponse;

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


    public static ConsultaResponse toResponse(Consulta consulta) {
        return new ConsultaResponse(
                consulta.getId(),
                consulta.getPacienteId(),
                consulta.getMedicoId(),
                consulta.getDataHora(),
                consulta.getExame(),
                consulta.getStatus()
        );
    }
}