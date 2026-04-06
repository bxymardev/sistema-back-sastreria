package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.dto.proveedor.representante.RepresentanteResponseDTO;
import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

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