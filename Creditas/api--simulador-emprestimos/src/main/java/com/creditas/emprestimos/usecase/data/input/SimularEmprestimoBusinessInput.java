package com.creditas.emprestimos.usecase.data.input;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class SimularEmprestimoBusinessInput {

    private BigDecimal valorEmprestimo;
    private LocalDate dataNascimento;
    private int prazoMeses;
}
