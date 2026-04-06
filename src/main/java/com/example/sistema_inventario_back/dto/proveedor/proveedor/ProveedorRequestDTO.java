package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProveedorRequestDTO {

    @NotBlank(message = "El nombre del encargo es obligatorio")
    private String nombresEncargado;

    @NotBlank(message = "El nombre comercial es obligatorio")
    private String nombreComercial;

    //Opcional
    private String identificacionFiscal;

    @NotBlank(message = "La direccion es obligatorio")
    private String direccion;

    @NotBlank(message = "El numero de telefono 1 es obligatorio")
    private String telefono1;

    //Opcional
    private String telefono2;

    @NotNull(message = "Tipo de Proveedor es obligatorio")
    private TipoProveedor tipoProveedor;
}