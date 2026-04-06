package com.example.sistema_inventario_back.entity.proveedor;

public enum NombreItemProveedor {
    HILO("HILO"),
    BOTON("BOTON"),
    TELA("TELA"),
    MAQUINA("MAQUINA DE COSER");

    private final String nombreLegible;

    NombreItemProveedor(String nombreLegible){
        this.nombreLegible = nombreLegible;
    }

    public String getNombreLegible;
}