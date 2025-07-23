package com.creditas.emprestimos.usecase.mappers;

import com.creditas.emprestimos.configs.MapperStructConfig;
import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;

@Mapper(config = MapperStructConfig.class)
public interface SimulacaoUseCaseMapper {

    @Mapping(source = "input.valorEmprestimo", target = "valorEmprestimo")
    @Mapping(source = "input.prazoMeses", target = "prazoMeses")
    SimulacaoEmprestimoBusinessOutput toSimulacaoEmprestimoBusinessOutput(
            SimularEmprestimoBusinessInput input,
            BigDecimal taxaJurosAnual,
            BigDecimal taxaJurosMensal,
            BigDecimal totalPago,
            BigDecimal totalJurosPagos,
            BigDecimal parcelaMensal);
}
