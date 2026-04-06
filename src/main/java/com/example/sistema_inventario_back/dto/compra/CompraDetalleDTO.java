package com.example.sistema_inventario_back.dto.compra;

import com.example.sistema_inventario_back.entity.compra.EstadoCompra;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CompraDetalleDTO {

    //INFORMACION DE LA COMPRA
    private Integer idCompra;
    private String numeroComprobante;
    private LocalDateTime fechaCompra;
    private LocalDateTime fechaActualizacion;
    private String notaEdicion;
    private BigDecimal subTotal;
    private BigDecimal descuento;
    private BigDecimal totalCompra;
    private String estadoCompra;

    //INFORMACION DEL PROVEEDOR
    private Integer idProveedor;
    private String nombreComercial;
    private String nombresEncargado;
    private String identificacionFiscal;
    private String numeroUno;
    private String numeroDos;
    private String direccion;
    private String tipoProveedor;

    //INFORMACION DEL USUARIO
    private Integer idUsuario;
    private String nombreUsuario;

    // ITEMS DE LA COMPRA (LISTA DE MATERIAS PRIMAS COMPRADAS)
    private List<CompraItemDetalleDTO> items;

    //RESUMEN
    private Integer cantidadItems;
}