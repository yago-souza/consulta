package com.fiap.postech.consultas.interfaces.controllers;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.CancelaConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.ConfirmaConsultaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final AgendarConsultaUseCase agendarConsulta;
    private final CancelaConsultaUseCase cancelaConsultaUseCase;
    private final ConfirmaConsultaUseCase confirmaConsultaUseCase;

    public ConsultaController(AgendarConsultaUseCase agendarConsulta, CancelaConsultaUseCase cancelaConsultaUseCase, ConfirmaConsultaUseCase confirmaConsultaUseCase) {
        this.agendarConsulta = agendarConsulta;
        this.cancelaConsultaUseCase = cancelaConsultaUseCase;
        this.confirmaConsultaUseCase = confirmaConsultaUseCase;
    }

    @PostMapping
    public ResponseEntity<String> agendar(@RequestBody AgendamentoRequest request) {
        Consulta consulta = request.toDomain();
        agendarConsulta.executar(consulta);
        return ResponseEntity.ok("Consulta agendada com sucesso");
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<Consulta> cancelarConsulta(@PathVariable UUID id) {
        Consulta consultaCancelada = cancelaConsultaUseCase.executar(id);
        return ResponseEntity.ok(consultaCancelada);
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<Consulta> confirmarConsulta(@PathVariable UUID id) {
        Consulta consultaConfirmada = confirmaConsultaUseCase.executar(id);
        return ResponseEntity.ok(consultaConfirmada);
    }
}
