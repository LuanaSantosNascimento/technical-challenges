package com.creditas.emprestimos.configs.http;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ControllerExceptionHandlerTest {

    private final ControllerExceptionHandler exceptionHandler = new ControllerExceptionHandler();

    @Test
    @DisplayName("Deve retornar BAD_REQUEST e mensagem adequada para HttpMessageNotReadableException")
    void testMessageNotReadableException() {
        HttpMessageNotReadableException ex = Mockito.mock(HttpMessageNotReadableException.class);

        ResponseEntity<String> response = exceptionHandler.messageNotReadableException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Erro na leitura da requisição: verifique se os dados enviados estão corretos.", response.getBody());
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST e lista de mensagens para MethodArgumentNotValidException")
    void testMethodArgumentNotValidException() {

        BindingResult bindingResult = Mockito.mock(BindingResult.class);
        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("SimularEmprestimoRequest", "dataNascimento", "O campo 'dataNascimento' é obrigatório."));
        fieldErrors.add(new FieldError("SimularEmprestimoRequest", "valorEmprestimo", "O campo 'valorEmprestimo' é obrigatório."));

        Mockito.when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = Mockito.mock(MethodArgumentNotValidException.class);
        Mockito.when(ex.getBindingResult()).thenReturn(bindingResult);

        ResponseEntity<ArrayList<String>> response = exceptionHandler.methodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().contains("O campo 'dataNascimento' é obrigatório."));
        assertTrue(response.getBody().contains("O campo 'valorEmprestimo' é obrigatório."));
    }

    @Test
    @DisplayName("Deve retornar BAD_REQUEST e mensagem de erro inesperado para Exception genérica")
    void testHandleException() {

        Exception ex = new Exception("Falha inesperada");

        ResponseEntity<String> response = exceptionHandler.handleException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Ocorreu um erro inesperado: Falha inesperada", response.getBody());
    }
}