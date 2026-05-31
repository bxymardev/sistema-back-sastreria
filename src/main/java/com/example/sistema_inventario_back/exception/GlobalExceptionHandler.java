package com.example.sistema_inventario_back.exception;

import com.example.sistema_inventario_back.exception.cliente.ClienteNoEncontradoException;
import com.example.sistema_inventario_back.exception.contrato.ContratoNoEncontradoException;
import com.example.sistema_inventario_back.exception.proveedor.CiudadNoEncontradoException;
import com.example.sistema_inventario_back.exception.proveedor.ProveedorNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar validaciones de @Valid
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidation(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(err -> errors.put(err.getField(), err.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }

    // Manejar genérico para RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ClienteNoEncontradoException.class)
    public ResponseEntity<String> handleClienteNotFound(ClienteNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ContratoNoEncontradoException.class)
    public ResponseEntity<String> handleContratoNotFound(ContratoNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ProveedorNoEncontradoException.class)
    public ResponseEntity<String> handleProveedorNotFound(ProveedorNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CiudadNoEncontradoException.class)
    public ResponseEntity<String> handleCiudadNotFound(CiudadNoEncontradoException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}