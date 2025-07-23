package com.creditas.emprestimos.controller.data.output;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SimulacaoEmprestimoResponse {

    @Schema(description = "Valor original solicitado no empréstimo", example = "1000.00")
    private BigDecimal valorEmprestimo;

    @Schema(description = "Prazo para pagamento em meses", example = "20")
    private int prazoMeses;

    @Schema(description = "Total pago ao final do empréstimo", example = "1031.80")
    private BigDecimal totalPago;

    @Schema(description = "Valor de cada parcela mensal", example = "51.59")
    private BigDecimal parcelaMensal;

    @Schema(description = "Total de juros pagos ao longo do prazo", example = "31.80")
    private BigDecimal totalJurosPagos;

    @Schema(description = "Taxa de juros anual", example = "0.03")
    private BigDecimal taxaJurosAnual;

    @Schema(description = "Taxa de juros mensal", example = "0.003")
    private BigDecimal taxaJurosMensal;
}
