package com.creditas.emprestimos.usecase.impl;

import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import com.creditas.emprestimos.usecase.mappers.SimulacaoUseCaseMapper;
import com.creditas.emprestimos.usecase.mappers.SimulacaoUseCaseMapperImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class EmprestimoUseCaseCargaTest {

    @Spy
    private SimulacaoUseCaseMapper mapper = new SimulacaoUseCaseMapperImpl();

    @InjectMocks
    private EmprestimoUseCaseImpl useCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Teste de carga: simulações em lote com dados iguais")
    void testDesempenhoCalculoEmprestimo() {

        SimularEmprestimoBusinessInput input = new SimularEmprestimoBusinessInput();
        input.setDataNascimento(LocalDate.now().minusYears(38));
        input.setValorEmprestimo(BigDecimal.valueOf(15000));
        input.setPrazoMeses(22);

        int iteracoes = 100_000;
        long start = System.nanoTime();

        for (int i = 0; i < iteracoes; i++) {
            SimulacaoEmprestimoBusinessOutput resultado = useCase.simular(input);
            assertNotNull(resultado);
        }

        long end = System.nanoTime();
        long duracaoMs = (end - start) / 1_000_000;
        System.out.println("Tempo total para " + iteracoes + " execuções: " + duracaoMs + " ms");
        System.out.println("Tempo médio por execução: " + (duracaoMs / (double) iteracoes) + " ms");
    }

    @DisplayName("Teste de carga: simulações em lote com dados variados")
    @ParameterizedTest(name = "Simulação com valor={0}, prazo={1}")
    @MethodSource("fornecerDados")
    void testeCargaSimulacoes(SimularEmprestimoBusinessInput input) {
        int repeticoes = 10_000;
        long inicio = System.nanoTime();

        for (int i = 0; i < repeticoes; i++) {
            SimulacaoEmprestimoBusinessOutput resultado = useCase.simular(input);
            assertNotNull(resultado);
        }

        long fim = System.nanoTime();
        long duracaoMs = (fim - inicio) / 1_000_000;

        System.out.println("Executadas " + repeticoes + " simulações para input " +
                input + " em " + duracaoMs + " ms");
    }

    static Stream<SimularEmprestimoBusinessInput> fornecerDados() {
        return Stream.of(
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(5000), LocalDate.of(1990, 5, 10), 24),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(10000), LocalDate.of(1985, 1, 1), 36),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(1500), LocalDate.of(2000, 12, 5), 12),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(20000), LocalDate.of(1975, 7, 20), 60),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(7500), LocalDate.of(1995, 3, 30), 18),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(3000), LocalDate.of(1980, 11, 11), 6),
                new SimularEmprestimoBusinessInput(BigDecimal.valueOf(70000), LocalDate.of(2020, 11, 11), 70)
        );
    }
}