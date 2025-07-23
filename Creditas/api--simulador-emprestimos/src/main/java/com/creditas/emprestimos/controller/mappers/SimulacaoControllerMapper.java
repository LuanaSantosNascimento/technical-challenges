package com.creditas.emprestimos.controller.mappers;

import com.creditas.emprestimos.configs.mappers.MapperStructConfig;
import com.creditas.emprestimos.controller.data.input.SimulacaoEmprestimoRequest;
import com.creditas.emprestimos.controller.data.output.SimulacaoEmprestimoResponse;
import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import org.mapstruct.Mapper;

@Mapper(config = MapperStructConfig.class)
public interface SimulacaoControllerMapper {

    SimularEmprestimoBusinessInput toSimulacaoBusinessInput(SimulacaoEmprestimoRequest request);

    SimulacaoEmprestimoResponse toSimulacaoEmprestimoResponse(SimulacaoEmprestimoBusinessOutput simulacao);
}
