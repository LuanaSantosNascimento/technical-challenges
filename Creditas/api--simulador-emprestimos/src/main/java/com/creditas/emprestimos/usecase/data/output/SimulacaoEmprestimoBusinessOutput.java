package com.creditas.emprestimos.usecase.data.output;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SimulacaoEmprestimoBusinessOutput {

    private BigDecimal valorEmprestimo;
    private int prazoMeses;
    private BigDecimal totalPago;
    private BigDecimal parcelaMensal;
    private BigDecimal totalJurosPagos;
    private BigDecimal taxaJurosAnual;
    private BigDecimal taxaJurosMensal;
}
