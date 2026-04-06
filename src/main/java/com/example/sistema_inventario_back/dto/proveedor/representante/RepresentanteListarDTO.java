package com.example.sistema_inventario_back.dto.proveedor.representante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepresentanteListarDTO {
    private String nombre;
    private String cargo;
    private String cedulaIdentidad;
    private String telefonoCelular;
    private LocalDateTime fechaCreacion;
}