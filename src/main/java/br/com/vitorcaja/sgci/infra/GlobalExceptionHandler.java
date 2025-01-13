package br.com.vitorcaja.sgci.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(ResponseStatusException ex) {
        Map<String, Object> errorDetails = new HashMap<>();
        int statusCode = ex.getStatusCode().value();

        errorDetails.put("status", statusCode);
        errorDetails.put("error", HttpStatus.valueOf(statusCode).getReasonPhrase()); // Obtém o motivo do status
        errorDetails.put("message", ex.getReason());
        errorDetails.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(errorDetails, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Coletar as mensagens de erro de validação
        List<String> errorMessages = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error->{
                    return String.format("%s - %s", error.getField(), error.getDefaultMessage());
                })  // Obter as mensagens de erro
                .collect(Collectors.toList());

        Map<String, Object> mapErrors = new HashMap<>();
        mapErrors.put("status", HttpStatus.BAD_REQUEST.value());
        mapErrors.put("error", HttpStatus.BAD_REQUEST.getReasonPhrase()); // Obtém o motivo do status
        mapErrors.put("messages", errorMessages);
        mapErrors.put("timestamp", LocalDateTime.now());

        return new ResponseEntity<>(mapErrors, HttpStatus.BAD_REQUEST);
    }
}
