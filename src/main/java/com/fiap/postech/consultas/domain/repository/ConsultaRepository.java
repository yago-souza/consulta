package com.fiap.postech.consultas.domain.repository;

import com.fiap.postech.consultas.domain.model.Consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ConsultaRepository {
    void salvar(Consulta consulta);
    List<Consulta> buscarConsultasParaDia(LocalDate data);
    List<Consulta> buscarConsultasParaHorario(LocalDateTime horario);
    List<Consulta> buscarConsultasEntre(LocalDateTime agora, LocalDateTime umaHoraDepois);
    Optional<Consulta> buscarPorId(UUID consultaId);
}
