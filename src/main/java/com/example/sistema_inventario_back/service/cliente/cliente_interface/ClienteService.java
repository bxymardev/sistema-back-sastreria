package com.example.sistema_inventario_back.service.cliente.cliente_interface;

import com.example.sistema_inventario_back.dto.cliente.ClienteDesactivarDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import com.example.sistema_inventario_back.dto.contrato.ContratoResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ClienteService {

    // Servicio para crear un nuevo cliente
    ClienteResponseDTO crearClienteService(ClienteRequestDTO clienteRequestDTO);

    // Servicio para listar a todos los clientes activos (paginado)
    ClienteListarPageDTO listarClientesService(Pageable pageable);

    // Servicio para buscar un cliente mediante el id
    ClienteResponseDTO buscarClienteById(Integer id);

    // Servicio para actualizar un cliente
    ClienteResponseDTO actualizarCliente(Integer id, ClienteRequestDTO clienteRequestDTO);

    // Servicio para buscar un cliente por cualquier campo (filtros)
    ClienteListarPageDTO buscarClienteFiltros(
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String telefonoUno,
            String carnetIdentidad,
            Pageable pageable
    );

    // Desactivar (eliminación lógica) un cliente
    ClienteResponseDTO desactivarCliente(Integer id, ClienteDesactivarDTO desactivarDTO);

    // Obtener todos los contratos de un cliente (incluidos finalizados y cancelados)
    List<ContratoResponseDTO> obtenerContratosDelCliente(Integer idCliente);
}