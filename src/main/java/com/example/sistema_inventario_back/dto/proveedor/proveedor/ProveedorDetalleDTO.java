package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import java.time.LocalDateTime;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorDetalleDTO {
    
    private Integer idProveedor;
    private String nombreComercial;
    private String nombresEncargado;
    private String identificacionFiscal;   // nullable
    private String numeroUno;
    private String numeroDos;              // nullable
    private String direccion;
    private TipoProveedor tipoProveedor;
    private Boolean estado;
    private String motivoEliminacion;      // nullable
    private LocalDateTime fechaCreacion;
    private LocalDateTime fechaActualizacion;
}