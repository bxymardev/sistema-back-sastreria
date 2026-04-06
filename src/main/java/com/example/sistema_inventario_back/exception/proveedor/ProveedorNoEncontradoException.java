package com.example.sistema_inventario_back.exception.proveedor;

public class ProveedorNoEncontradoException extends RuntimeException{
    public ProveedorNoEncontradoException(Integer id){
        super("Proveedor no encontrado con ID: " +id);
    }
}