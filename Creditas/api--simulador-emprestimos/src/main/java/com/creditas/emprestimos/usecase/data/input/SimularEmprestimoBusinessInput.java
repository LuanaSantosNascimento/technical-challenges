package com.creditas.emprestimos.usecase.data.input;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class SimularEmprestimoBusinessInput {

    private BigDecimal valorEmprestimo;
    private LocalDate dataNascimento;
    private int prazoMeses;
}
