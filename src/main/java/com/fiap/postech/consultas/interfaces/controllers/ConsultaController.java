package com.fiap.postech.consultas.interfaces.controllers;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.BuscarTodasConsultasUseCase;
import com.fiap.postech.consultas.application.usecases.CancelaConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.ConfirmaConsultaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.repository.mapper.ConsultaMapper;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequest;
import com.fiap.postech.consultas.interfaces.dtos.ConsultaResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/consultas")
public class ConsultaController {

    private final AgendarConsultaUseCase agendarConsulta;
    private final CancelaConsultaUseCase cancelaConsultaUseCase;
    private final ConfirmaConsultaUseCase confirmaConsultaUseCase;
    private final BuscarTodasConsultasUseCase buscarTodasConsultasUseCase;

    public ConsultaController(AgendarConsultaUseCase agendarConsulta, CancelaConsultaUseCase cancelaConsultaUseCase, ConfirmaConsultaUseCase confirmaConsultaUseCase, BuscarTodasConsultasUseCase buscarTodasConsultasUseCase) {
        this.agendarConsulta = agendarConsulta;
        this.cancelaConsultaUseCase = cancelaConsultaUseCase;
        this.confirmaConsultaUseCase = confirmaConsultaUseCase;
        this.buscarTodasConsultasUseCase = buscarTodasConsultasUseCase;
    }

    @GetMapping
    public ResponseEntity<List<ConsultaResponse>> listarConsultas() {
        List<Consulta> consultas = buscarTodasConsultasUseCase.executar();
        List<ConsultaResponse> response = consultas.stream()
                .map(ConsultaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
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
