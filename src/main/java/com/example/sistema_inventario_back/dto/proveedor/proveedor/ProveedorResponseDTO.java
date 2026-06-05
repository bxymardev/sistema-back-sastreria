package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ProveedorResponseDTO {
    private Integer idProveedor;
    private String nombreComercial;
    private String identificacionFiscal;
    private String direccion;
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
    private TipoProveedor tipoProveedor;
}