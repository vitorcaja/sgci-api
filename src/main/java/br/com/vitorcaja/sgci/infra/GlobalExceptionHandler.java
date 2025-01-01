package br.com.vitorcaja.sgci.infra;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

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
}
