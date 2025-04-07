package com.fiap.postech.consultas.interfaces.controllers;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final AgendarConsultaUseCase agendarConsulta;

    public ConsultaController(AgendarConsultaUseCase agendarConsulta) {
        this.agendarConsulta = agendarConsulta;
    }

    @PostMapping
    public ResponseEntity<String> agendar(@RequestBody AgendamentoRequest request) {
        Consulta consulta = request.toDomain();
        agendarConsulta.executar(consulta);
        return ResponseEntity.ok("Consulta agendada com sucesso");
    }
}
