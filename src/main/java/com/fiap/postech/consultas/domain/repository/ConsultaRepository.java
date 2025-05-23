package com.fiap.postech.consultas.domain.repository;

import com.fiap.postech.consultas.domain.model.Consulta;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ConsultaRepository {
    Consulta salvar(Consulta consulta);
    Optional<Consulta> buscarPorId(Long consultaId);
    List<Consulta> buscarConsultasEntre(LocalDateTime agora, LocalDateTime umaHoraDepois);
    List<Consulta> buscarConsultasParaHorario(LocalDateTime horario);
    List<Consulta> buscarTodas();
}
