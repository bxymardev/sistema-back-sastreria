package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProveedorUpdateDTO {
    
    @NotBlank(message = "El nombre del encargo es obligatorio")
    private String nombresEncargado;

    @NotBlank(message = "El nombre comercial es obligatorio")
    private String nombreComercial;

    // Opcional para edición
    private String identificacionFiscal;

    @NotBlank(message = "La direccion es obligatorio") 
    private String direccion;

    @NotBlank(message = "El número del telefono uno es obligatorio")
    private String telefono1;

    // Opcional para telefono 2
    private String telefono2;

    @NotNull(message = "Tipo de proveedor es obligatorio")
    private TipoProveedor tipoProveedor;
}