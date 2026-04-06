package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.compra.EstadoCompra;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class CompraResponseDTO {

    private Integer idCompra;
    private String numeroComprobante;
    private BigDecimal subTotal;
    private BigDecimal descuento;
    private BigDecimal totalCompra;
    private EstadoCompra estadoCompra;
    private String notaEdicion;
    private LocalDateTime fechaCompra;

    // Proveedor
    private Integer idProveedor;
    private String nombreProveedor;

    // Usuario
    private String usuario;

    // Detalles
    private List<CompraDetalleResponseDTO> detalles;
}