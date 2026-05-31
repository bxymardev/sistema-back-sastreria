package com.example.sistema_inventario_back.exception.cliente;

public class ClienteNoEncontradoException extends RuntimeException {
    public ClienteNoEncontradoException(Integer id) {
        super("Cliente no encontrado con id: " + id);
    }
}
