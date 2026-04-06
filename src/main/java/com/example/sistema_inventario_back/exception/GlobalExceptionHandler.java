package com.example.sistema_inventario_back.exception;

import com.example.sistema_inventario_back.exception.proveedor.CiudadNoEncontradoException;
import com.example.sistema_inventario_back.exception.proveedor.ProveedorNoEncontradoException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Manejar genérico para RuntimeException
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(ProveedorNoEncontradoException.class)
    public ResponseEntity<String> handleProveedorNotFound(ProveedorNoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(CiudadNoEncontradoException.class)
    public ResponseEntity<String> handleCiudadNotFound(CiudadNoEncontradoException ex){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}