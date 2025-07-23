package com.creditas.emprestimos.controller.data.input;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SimulacaoEmprestimoRequest {

    @NotNull(message = "O campo 'valorEmprestimo' é obrigatório.")
    @Schema(description = "Valor solicitado no empréstimo", example = "1000.00")
    private BigDecimal valorEmprestimo;

    @NotNull(message = "O campo 'dataNascimento' é obrigatório.")
    @Schema(description = "Data de nascimento do solicitante", example = "1990-01-14")
    private LocalDate dataNascimento;

    @Min(value = 1, message = "O campo 'prazoMeses' deve ser maior que zero.")
    @Schema(description = "Prazo para pagamento em meses", example = "20")
    private int prazoMeses;
}
