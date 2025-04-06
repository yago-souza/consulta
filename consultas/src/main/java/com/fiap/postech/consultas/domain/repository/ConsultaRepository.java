package com.fiap.postech.consultas.domain.repository;

import com.fiap.postech.consultas.domain.Consulta;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository {
    void salvar(Consulta consulta);
    List<Consulta> buscarConsultasParaDia(LocalDate data);
    List<Consulta> buscarConsultasParaHorario(LocalDateTime horario);
    List<Consulta> buscarConsultasEntre(LocalDateTime agora, LocalDateTime umaHoraDepois);
}
