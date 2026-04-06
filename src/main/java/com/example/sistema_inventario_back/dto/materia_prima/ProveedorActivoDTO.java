package com.example.sistema_inventario_back.dto.materia_prima;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorActivoDTO {
    private Integer idProveedor;
    private String nombreComercial;
}