package com.creditas.emprestimos.usecase.impl;

import com.creditas.emprestimos.usecase.EmprestimoUseCase;
import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import com.creditas.emprestimos.usecase.mappers.SimulacaoUseCaseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Service
@RequiredArgsConstructor
public class EmprestimoUseCaseImpl implements EmprestimoUseCase {

    private static final BigDecimal TAXA_JUROS_ATE_25 = BigDecimal.valueOf(0.05);
    private static final BigDecimal TAXA_JUROS_26_A_40 = BigDecimal.valueOf(0.03);
    private static final BigDecimal TAXA_JUROS_41_A_60 = BigDecimal.valueOf(0.02);
    private static final BigDecimal TAXA_JUROS_ACIMA_60 = BigDecimal.valueOf(0.04);
    private static final int CASAS_DECIMAIS_PARCELA = 2;
    private final SimulacaoUseCaseMapper mapper;

    @Override
    public SimulacaoEmprestimoBusinessOutput simular(SimularEmprestimoBusinessInput input) {

        BigDecimal taxaJurosAnual = calcularTaxaJurosAnual(input.getDataNascimento());
        BigDecimal taxaJurosMensal = taxaJurosAnual.divide(BigDecimal.valueOf(12), 3, RoundingMode.HALF_EVEN);
        BigDecimal parcelaMensal = calcularValorParcelaMensal(input.getValorEmprestimo(), input.getPrazoMeses(), taxaJurosMensal);

        BigDecimal totalPago = parcelaMensal.multiply(BigDecimal.valueOf(input.getPrazoMeses()));
        BigDecimal totalJurosPagos = totalPago.subtract(input.getValorEmprestimo());

        return mapper.toSimulacaoEmprestimoBusinessOutput(input,
                taxaJurosAnual,
                taxaJurosMensal,
                totalPago,
                totalJurosPagos,
                parcelaMensal);
    }

    private BigDecimal calcularValorParcelaMensal(BigDecimal valorEmprestimo,
                                             int prazoEmMeses,
                                             BigDecimal taxaJurosMensal) {

        MathContext mc = new MathContext(5, RoundingMode.HALF_EVEN);

        BigDecimal umMaisJuros = taxaJurosMensal.add(BigDecimal.ONE);
        BigDecimal potenciaNegativa = calcularPotenciaNegativa(umMaisJuros, prazoEmMeses, mc);

        // Fórmula: PMT = PV * r / (1 - (1 + r)^-n)
        BigDecimal denominador = BigDecimal.ONE.subtract(potenciaNegativa, mc);
        return valorEmprestimo.multiply(taxaJurosMensal).divide(denominador, CASAS_DECIMAIS_PARCELA, RoundingMode.HALF_EVEN);
    }

    private BigDecimal calcularPotenciaNegativa(BigDecimal base, int expoente, MathContext mc) {
        return BigDecimal.ONE.divide(base.pow(expoente, mc), mc);// (1 + r)^(-n)
    }

    private BigDecimal calcularTaxaJurosAnual(LocalDate dataNascimento) {

        int idade = Period.between(dataNascimento, LocalDate.now()).getYears();
        if (idade <= 25) {
            return TAXA_JUROS_ATE_25;
        } else if (idade <= 40) {
            return TAXA_JUROS_26_A_40;
        } else if (idade <= 60) {
            return TAXA_JUROS_41_A_60;
        } else {
            return TAXA_JUROS_ACIMA_60;
        }
    }
}
