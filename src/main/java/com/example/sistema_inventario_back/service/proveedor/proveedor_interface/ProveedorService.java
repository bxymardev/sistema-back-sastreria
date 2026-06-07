package com.example.sistema_inventario_back.service.proveedor.proveedor_interface;

import com.example.sistema_inventario_back.dto.materia_prima.ProveedorActivoDTO;
import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProveedorService {

    //Servicio para crear un nuevo Proveedor
    ProveedorResponseDTO createProveedor(ProveedorRequestDTO proveedorRequestDTO);

    //Servicio para listar a todos los Proveedores
    ProveedorPageResponseDTO getAllProveedores(Pageable pageable);

    // Servicio para listar a todos los Proveedores (nueva version)
    ProveedorPageListarDTO getAllProveedoresNew(Pageable pageable, Boolean estado);

    //Servicio para obtener un Proveedor por ID
    ProveedorResponseDTO getProveedorById(Integer id);

    // Servicio para obtener el detalle completo de un proveedor
    ProveedorDetalleDTO getDetalleProveedor(Integer id);

    //Servicio para actualizar el Proveedor
    ProveedorResponseDTO updateProveedor(Integer id, ProveedorUpdateDTO proveedorRequestDTO);

    // Servicio para buscar un proveedor por filtros multiples
    ProveedorPageListarDTO buscarProveedorConFiltros(String searchTerm, Pageable pageable);

    //Servicio para cambiar el nombre del proveedor
    ProveedorResponseDTO cambiarNombreProveedor(ProveedorCambiarNombreDTO nuevoNombre);

    // Servicio para obtener indicadores
    ProveedorIndicadoresDTO obtenerIndicadoresService();

    // Servicio para listar proveedores activos
    List<ProveedorActivoDTO> getProveedoresActivos();

    // Servicio para listar a todos los proveedores sin paginacion
    List<ProveedorListarDTO> getAllProveedoresSinPaginacion(Boolean estado);
}