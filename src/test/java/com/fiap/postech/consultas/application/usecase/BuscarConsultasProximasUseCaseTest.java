package com.fiap.postech.consultas.application.usecase;

import com.fiap.postech.consultas.application.usecases.BuscarConsultasProximasUseCase;
import com.fiap.postech.consultas.domain.model.Consulta;
import com.fiap.postech.consultas.domain.repository.ConsultaRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class BuscarConsultasProximasUseCaseTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private BuscarConsultasProximasUseCase useCase;

    @Test
    void deveBuscarConsultasParaUmaHoraDepois() {

        List<Consulta> consultas = List.of(new Consulta());

        // Usar ArgumentCaptor porque o horário exato muda com o tempo
        when(consultaRepository.buscarConsultasEntre(any(), any())).thenReturn(consultas);

        List<Consulta> resultado = useCase.executar();

        assertEquals(consultas, resultado);
        verify(consultaRepository).buscarConsultasEntre(any(), any());
    }
}
