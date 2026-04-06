package com.example.sistema_inventario_back.exception.proveedor;

public class CiudadNoEncontradoException extends RuntimeException{
    public CiudadNoEncontradoException(Integer id){
        super("Ciudad no encontrada con ID: "+id);
    }
}