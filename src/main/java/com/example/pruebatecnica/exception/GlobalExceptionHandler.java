package com.example.pruebatecnica.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, Object> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            errors.put(error.getField(), error.getDefaultMessage());
        });
        errors.put("success", false);

        return ResponseEntity.badRequest().body(errors);
    }

      @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<Map<String, Object>> handleInvalidFormat(HttpMessageNotReadableException ex) {
        String mensaje = "Formato de dato inválido en la solicitud. Por favor verifica tus campos.";
        
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getTargetType().equals(LocalDate.class)) {
                mensaje = "La fecha tiene un formato inválido o valores no permitidos (utiliza una fecha valida en formato yyyy-MM-dd).";
            }
        }
        
        Map<String, Object> error = new HashMap<>();
        error.put("error", mensaje);
        error.put("success", false);

        return ResponseEntity.badRequest().body(error);
    }
}