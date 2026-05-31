package com.example.sistema_inventario_back.exception.contrato;

public class ContratoNoEncontradoException extends RuntimeException {
    public ContratoNoEncontradoException(Integer id) {
        super("Contrato no encontrado con id: " + id);
    }
}
