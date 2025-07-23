package com.creditas.emprestimos.usecase;

import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;

public interface EmprestimoUseCase {
    SimulacaoEmprestimoBusinessOutput simular(SimularEmprestimoBusinessInput command);
}
