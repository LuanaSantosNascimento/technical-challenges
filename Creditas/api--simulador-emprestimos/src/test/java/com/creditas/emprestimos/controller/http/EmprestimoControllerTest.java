package com.creditas.emprestimos.controller.http;

import com.creditas.emprestimos.configs.http.ControllerExceptionHandler;
import com.creditas.emprestimos.controller.data.input.SimulacaoEmprestimoRequest;
import com.creditas.emprestimos.controller.data.output.SimulacaoEmprestimoResponse;
import com.creditas.emprestimos.controller.mappers.SimulacaoControllerMapper;
import com.creditas.emprestimos.controller.mappers.SimulacaoControllerMapperImpl;
import com.creditas.emprestimos.usecase.EmprestimoUseCase;
import com.creditas.emprestimos.usecase.data.output.SimulacaoEmprestimoBusinessOutput;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

class EmprestimoControllerTest {

    @Mock
    private EmprestimoUseCase simularEmprestimoUseCase;

    @Spy
    private SimulacaoControllerMapper mapper = new SimulacaoControllerMapperImpl();

    @InjectMocks
    private EmprestimoController controller;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(controller)
                .setControllerAdvice(new ControllerExceptionHandler()).build();
        objectMapper = new ObjectMapper().registerModule(new JavaTimeModule());
    }

    @Test
    @DisplayName("Deve retornar ResponseEntity com SimulacaoEmprestimoResponse")
    void deveRetornarResponseEntityComSimulacaoEmprestimoResponse() {
        SimulacaoEmprestimoRequest request = mock(SimulacaoEmprestimoRequest.class);
        SimulacaoEmprestimoBusinessOutput output = buildBusinessOutput();
        SimulacaoEmprestimoResponse response = buildResponse();

        when(simularEmprestimoUseCase.simular(any())).thenReturn(output);

        ResponseEntity<SimulacaoEmprestimoResponse> result = controller.simularEmprestimo(request);

        assertNotNull(result);
        assertEquals(response.getValorEmprestimo(), result.getBody().getValorEmprestimo());
        assertEquals(response.getParcelaMensal(), result.getBody().getParcelaMensal());
        assertEquals(response.getPrazoMeses(), result.getBody().getPrazoMeses());
        assertEquals(response.getTotalPago(), result.getBody().getTotalPago());
        assertEquals(response.getTotalJurosPagos(), result.getBody().getTotalJurosPagos());
        assertEquals(response.getTaxaJurosAnual(), result.getBody().getTaxaJurosAnual());
        assertEquals(response.getTaxaJurosMensal(), result.getBody().getTaxaJurosMensal());
    }

    @Test
    @DisplayName("Deve retornar erro quando dataNascimento não for informada")
    void deveRetornarErroQuandoDataNascimentoNaoInformada() throws Exception {
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        request.setValorEmprestimo(new BigDecimal("1000"));
        request.setPrazoMeses(20);

        mockMvc.perform(post("/api/v1/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"O campo 'dataNascimento' é obrigatório.\"]"));
    }

    @Test
    @DisplayName("Deve retornar erro quando prazoMeses não for informada")
    void deveRetornarErroQuandoPrazoMesesNaoInformada() throws Exception {
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        request.setValorEmprestimo(new BigDecimal("1000"));
        request.setDataNascimento(LocalDate.of(1990, 1, 14));

        mockMvc.perform(post("/api/v1/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"O campo 'prazoMeses' deve ser maior que zero.\"]"));
    }

    @Test
    @DisplayName("Deve retornar erro quando valorEmprestimo não for informada")
    void deveRetornarErroQuandoValorEmprestimoNaoInformada() throws Exception {
        SimulacaoEmprestimoRequest request = new SimulacaoEmprestimoRequest();
        request.setPrazoMeses(25);
        request.setDataNascimento(LocalDate.of(1990, 1, 14));

        mockMvc.perform(post("/api/v1/emprestimos/simular")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("[\"O campo 'valorEmprestimo' é obrigatório.\"]"));
    }


    private SimulacaoEmprestimoBusinessOutput buildBusinessOutput() {

        SimulacaoEmprestimoBusinessOutput output = new SimulacaoEmprestimoBusinessOutput();
        output.setValorEmprestimo(new BigDecimal("1200.00"));
        output.setParcelaMensal(new BigDecimal("60.00"));
        output.setPrazoMeses(20);
        output.setTotalPago(new BigDecimal("1500.00"));
        output.setTotalJurosPagos(new BigDecimal("300.00"));
        output.setTaxaJurosAnual(new BigDecimal("0.25"));
        output.setTaxaJurosMensal(new BigDecimal("0.015"));
        return output;
    }

    private SimulacaoEmprestimoResponse buildResponse() {

        SimulacaoEmprestimoResponse response = new SimulacaoEmprestimoResponse();
        response.setValorEmprestimo(new BigDecimal("1200.00"));
        response.setParcelaMensal(new BigDecimal("60.00"));
        response.setPrazoMeses(20);
        response.setTotalPago(new BigDecimal("1500.00"));
        response.setTotalJurosPagos(new BigDecimal("300.00"));
        response.setTaxaJurosAnual(new BigDecimal("0.25"));
        response.setTaxaJurosMensal(new BigDecimal("0.015"));
        return response;
    }
}