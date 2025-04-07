package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.BuscarConsultasDoDiaUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarConsultasDoDiaUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private BuscarConsultasDoDiaUseCase useCase;

    @Test
    void deveBuscarConsultasDoDiaAtual() {
        LocalDate hoje = LocalDate.now();
        List<Consulta> consultasEsperadas = List.of(new Consulta(), new Consulta());

        when(consultaRepository.buscarConsultasParaDia(hoje)).thenReturn(consultasEsperadas);

        List<Consulta> resultado = useCase.executar();

        assertEquals(consultasEsperadas, resultado);
        verify(consultaRepository).buscarConsultasParaDia(hoje);
    }
}
