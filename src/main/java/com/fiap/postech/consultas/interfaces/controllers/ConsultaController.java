package com.fiap.postech.consultas.interfaces.controllers;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.BuscarTodasConsultasUseCase;
import com.fiap.postech.consultas.application.usecases.CancelaConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.ConfirmaConsultaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.repository.mapper.ConsultaMapper;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequest;
import com.fiap.postech.consultas.interfaces.dtos.ConsultaResponseDTO;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<List<ConsultaResponseDTO>> listarConsultas() {
        List<Consulta> consultas = buscarTodasConsultasUseCase.executar();
        List<ConsultaResponseDTO> response = consultas.stream()
                .map(ConsultaMapper::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ConsultaResponseDTO> agendar(@RequestBody AgendamentoRequest dto) {
        Consulta novaConsulta = ConsultaMapper.toDomainFromDTO(dto);
        Consulta consultaSalva = agendarConsulta.executar(novaConsulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaMapper.toResponse(consultaSalva));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(@PathVariable UUID id) {
        Consulta consulta = cancelaConsultaUseCase.executar(id);
        return ResponseEntity.ok(ConsultaMapper.toResponse(consulta));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ConsultaResponseDTO> confirmar(@PathVariable UUID id) {
        Consulta consulta = confirmaConsultaUseCase.executar(id);
        return ResponseEntity.ok(ConsultaMapper.toResponse(consulta));
    }
}
