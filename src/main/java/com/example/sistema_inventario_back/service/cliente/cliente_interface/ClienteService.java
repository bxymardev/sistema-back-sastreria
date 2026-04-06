package com.example.sistema_inventario_back.service.cliente.cliente_interface;

import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import org.springframework.data.domain.Pageable;

public interface ClienteService {

    // Servicio para crear un nuevo cliente
    ClienteResponseDTO crearClienteService(ClienteRequestDTO clienteRequestDTO);

    // Servicio para listar a todos los clientes
    ClienteListarPageDTO listarClientesService(Pageable pageable);

    // Servicio para buscar un cliente mediante el id
    ClienteResponseDTO buscarClienteById(Integer id);

    // Servicio para actualizar un cliente
    ClienteResponseDTO actualizarCliente(Integer id, ClienteRequestDTO clienteRequestDTO);

    // Servicio para buscar un cliente por cualquier campo
    ClienteListarPageDTO buscarClienteFiltros(
            String nombre,
            String apellidoPaterno,
            String apellidoMaterno,
            String telefono1,
            Pageable pageable
    );
}