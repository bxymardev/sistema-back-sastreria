package com.example.sistema_inventario_back.service.compra.compra_interface;

import com.example.sistema_inventario_back.dto.compra.*;
import org.springframework.data.domain.Pageable;

public interface CompraService {

    // Servicio para listar a todas las compras
    CompraPageListarDTO getAllComprasPaginationService(Pageable pageable);

    // Servicio para crear una nueva compra
    CompraResponseDTO createCompraService(CompraRequestDTO requestDTO, String username);

    // Servicio para estadisticas
    CompraEstadisticaDTO getEstadisticasService();

    // Servicio para obtener el detalle completo de una compra
    CompraDetalleDTO obtenerDetalleCompraService(Integer idCompra);
}