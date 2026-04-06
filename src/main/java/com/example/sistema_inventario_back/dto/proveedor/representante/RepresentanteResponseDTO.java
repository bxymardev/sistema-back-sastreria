package com.example.sistema_inventario_back.dto.proveedor.representante;

import com.example.sistema_inventario_back.entity.proveedor.GeneroRepresentante;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RepresentanteResponseDTO {
    private Integer idRepresentante;
    private String nombre;
    private String cargo;
    private String cedulaIdentidad;
    private GeneroRepresentante generoRepresentante;
    private String telefonoFijo;
    private String telefonoCelular;
    private String correoElectronico;
    private String direccion;
    private String observaciones;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private Integer idProveedor;
    private String nombreComercial;
}
