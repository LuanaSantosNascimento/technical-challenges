package com.creditas.emprestimos.usecase.impl;

import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import com.creditas.emprestimos.usecase.mappers.SimulacaoUseCaseMapper;
import com.creditas.emprestimos.usecase.mappers.SimulacaoUseCaseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class EmprestimoUseCaseImplTest {

    @Spy
    private SimulacaoUseCaseMapper mapper = new SimulacaoUseCaseMapperImpl();

    @InjectMocks
    private EmprestimoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Deve simular empréstimo para idade até 25 anos e validar valores calculados")
    void deveSimularEmprestimoParaIdadeAte25() {
        SimularEmprestimoBusinessInput input = new SimularEmprestimoBusinessInput();
        input.setDataNascimento(LocalDate.now().minusYears(20));
        input.setValorEmprestimo(BigDecimal.valueOf(10000));
        input.setPrazoMeses(12);

        SimulacaoEmprestimoBusinessOutput output = useCase.simular(input);

        assertNotNull(output);
        verify(mapper).toSimulacaoEmprestimoBusinessOutput(eq(input), eq(BigDecimal.valueOf(0.05)), any(), any(), any(), any());

        // Valores esperados
        BigDecimal taxaJurosAnualEsperada = BigDecimal.valueOf(0.05);
        BigDecimal taxaJurosMensalEsperada = taxaJurosAnualEsperada.divide(BigDecimal.valueOf(12), 3, RoundingMode.HALF_EVEN);
        BigDecimal pmtEsperado = new BigDecimal("854.70");

        assertEquals(taxaJurosAnualEsperada, output.getTaxaJurosAnual());
        assertEquals(taxaJurosMensalEsperada, output.getTaxaJurosMensal());
        assertEquals(pmtEsperado, output.getParcelaMensal());
    }

    @Test
    @DisplayName("Deve simular empréstimo para idade até 26 e 40 anos e validar valores calculados")
    void deveSimularEmprestimoParaIdadeEntre26e40() {
        SimularEmprestimoBusinessInput input = new SimularEmprestimoBusinessInput();
        input.setDataNascimento(LocalDate.now().minusYears(30));
        input.setValorEmprestimo(BigDecimal.valueOf(5000));
        input.setPrazoMeses(27);

        SimulacaoEmprestimoBusinessOutput output = useCase.simular(input);

        assertNotNull(output);
        // Valores esperados
        BigDecimal taxaJurosAnualEsperada = BigDecimal.valueOf(0.03);
        BigDecimal taxaJurosMensalEsperada = taxaJurosAnualEsperada.divide(BigDecimal.valueOf(12), 3, RoundingMode.HALF_EVEN);
        BigDecimal pmtEsperado = new BigDecimal("190.51");

        assertEquals(taxaJurosAnualEsperada, output.getTaxaJurosAnual());
        assertEquals(taxaJurosMensalEsperada, output.getTaxaJurosMensal());
        assertEquals(pmtEsperado, output.getParcelaMensal());
    }

    @Test
    void deveSimularEmprestimoParaIdadeEntre41e60() {
        SimularEmprestimoBusinessInput input = new SimularEmprestimoBusinessInput();
        input.setDataNascimento(LocalDate.now().minusYears(50));
        input.setValorEmprestimo(BigDecimal.valueOf(8000));
        input.setPrazoMeses(36);

        when(mapper.toSimulacaoEmprestimoBusinessOutput(any(), any(), any(), any(), any(), any()))
                .thenReturn(new SimulacaoEmprestimoBusinessOutput());

        SimulacaoEmprestimoBusinessOutput output = useCase.simular(input);

        assertNotNull(output);
        verify(mapper).toSimulacaoEmprestimoBusinessOutput(eq(input), eq(BigDecimal.valueOf(0.02)), any(), any(), any(), any());
    }

    @Test
    void deveSimularEmprestimoParaIdadeAcimaDe60() {
        SimularEmprestimoBusinessInput input = new SimularEmprestimoBusinessInput();
        input.setDataNascimento(LocalDate.now().minusYears(65));
        input.setValorEmprestimo(BigDecimal.valueOf(12000));
        input.setPrazoMeses(48);

        SimulacaoEmprestimoBusinessOutput output = useCase.simular(input);

        assertNotNull(output);
        verify(mapper).toSimulacaoEmprestimoBusinessOutput(eq(input), eq(BigDecimal.valueOf(0.04)), any(), any(), any(), any());
    }

    @Test
    void deveLancarExcecaoParaInputNulo() {
        assertThrows(NullPointerException.class, () -> useCase.simular(null));
    }
}