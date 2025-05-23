package com.fiap.postech.consultas.interfaces.controllers;

import com.fiap.postech.consultas.application.usecases.AgendarConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.BuscarTodasConsultasUseCase;
import com.fiap.postech.consultas.application.usecases.CancelaConsultaUseCase;
import com.fiap.postech.consultas.application.usecases.ConfirmaConsultaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.infrastructure.repository.mapper.ConsultaMapper;
import com.fiap.postech.consultas.interfaces.dtos.AgendamentoRequestDTO;
import com.fiap.postech.consultas.interfaces.dtos.ConsultaResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
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
    public ResponseEntity<ConsultaResponseDTO> agendar(@RequestBody AgendamentoRequestDTO dto) {
        Consulta novaConsulta = ConsultaMapper.toDomainFromDTO(dto);
        Consulta consultaSalva = agendarConsulta.executar(novaConsulta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ConsultaMapper.toResponse(consultaSalva));
    }

    @PatchMapping("/{id}/cancelar")
    public ResponseEntity<ConsultaResponseDTO> cancelar(@PathVariable Long id) {
        Consulta consulta = cancelaConsultaUseCase.executar(id);
        return ResponseEntity.ok(ConsultaMapper.toResponse(consulta));
    }

    @PatchMapping("/{id}/confirmar")
    public ResponseEntity<ConsultaResponseDTO> confirmar(@PathVariable Long id) {
        Consulta consulta = confirmaConsultaUseCase.executar(id);
        return ResponseEntity.ok(ConsultaMapper.toResponse(consulta));
    }

    @GetMapping("/teste-jwt")
    public ResponseEntity<String> testeJwt() {
        return ResponseEntity.ok("JWT funcionando!");
    }
}
