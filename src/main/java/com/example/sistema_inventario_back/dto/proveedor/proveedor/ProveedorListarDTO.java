package com.example.sistema_inventario_back.dto.proveedor.proveedor;

import com.example.sistema_inventario_back.entity.proveedor.TipoProveedor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProveedorListarDTO {
    private Integer idProveedor;
    private String nombresEncargado;
    private String nombreComercial;
    private String identificacionFiscal;
    private String direccion;
    private LocalDateTime fechaCreacion;
    private String numeroUno;
    private String numeroDos;
    private TipoProveedor tipoProveedor;
    private Boolean estado;
    private BigDecimal totalCompras;
    private LocalDateTime ultimaCompra;
}