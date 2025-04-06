package com.fiap.postech.consultas.application.services;

import com.fiap.postech.consultas.domain.Consulta;

public interface NotificacaoService {
    void enviarLembreteConsulta(Consulta consulta);
}
