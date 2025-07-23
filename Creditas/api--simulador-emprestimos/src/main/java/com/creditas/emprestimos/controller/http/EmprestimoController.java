package com.creditas.emprestimos.controller.http;

import com.creditas.emprestimos.controller.data.input.SimulacaoEmprestimoRequest;
import com.creditas.emprestimos.controller.data.output.SimulacaoEmprestimoResponse;
import com.creditas.emprestimos.controller.mappers.SimulacaoControllerMapper;
import com.creditas.emprestimos.usecase.EmprestimoUseCase;
import com.creditas.emprestimos.usecase.data.input.SimularEmprestimoBusinessInput;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "API de Simulação de Empréstimos",
                version = "1.0",
                description = "Simula empréstimos com base em idade, valor e prazo."
        ))
@RestController
@RequestMapping("/api/v1/emprestimos")
@RequiredArgsConstructor
public class EmprestimoController {

    private final EmprestimoUseCase simularEmprestimoUseCase;
    private final SimulacaoControllerMapper mapper;

    @Operation(summary = "Simular Empréstimo", tags = {"Empréstimos"})
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Simulação realizada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Requisição inválida, verifique os dados informados"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor")
    })
    @PostMapping("/simular")
    public ResponseEntity<SimulacaoEmprestimoResponse> simularEmprestimo(
            @Valid @RequestBody SimulacaoEmprestimoRequest request) {

        SimularEmprestimoBusinessInput input = mapper.toSimulacaoBusinessInput(request);
        SimulacaoEmprestimoBusinessOutput simulacao = simularEmprestimoUseCase.simular(input);
        SimulacaoEmprestimoResponse response = mapper.toSimulacaoEmprestimoResponse(simulacao);

        return ResponseEntity.ok(response);
    }
}
