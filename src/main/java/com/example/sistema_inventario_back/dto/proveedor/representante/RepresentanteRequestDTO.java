package com.example.sistema_inventario_back.dto.proveedor.representante;

import com.example.sistema_inventario_back.entity.proveedor.GeneroRepresentante;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RepresentanteRequestDTO {
    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El cargo es obligatorio")
    private String cargo;

    @NotBlank(message = "La cédula de identidad es obligatorio")
    private String cedulaIdentidad;

    @NotNull(message = "La cedula de identidad es obligatorio")
    private GeneroRepresentante generoRepresentante;

    //Opcional
    private String telefonoFijo;

    @NotBlank(message = "El telefono celular es obligatorio")
    private String telefonoCelular;

    //Opcional
    private String correoElectronico;

    //Opcional
    private String direccion;

    //Opcional
    private String observaciones;

    @NotNull(message = "El id del proveedor es obligatorio")
    private Integer idProveedor;
}