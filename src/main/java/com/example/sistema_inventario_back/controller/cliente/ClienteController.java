package com.example.sistema_inventario_back.controller.cliente;

import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import com.example.sistema_inventario_back.service.cliente.cliente_interface.ClienteService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired final ClienteService clienteService;

    // Controlador para crear un nuevo Cliente
    @PostMapping("/crearCliente")
    public ResponseEntity<ClienteResponseDTO> crearClienteController(
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO
            ){
        ClienteResponseDTO clienteResponseDTO = clienteService.crearClienteService(clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // Controlador para listar a todos los clientes
    @GetMapping("/listarClientesActivos")
    public ResponseEntity<ClienteListarPageDTO> listarClientesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCliente") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){

        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);

        ClienteListarPageDTO responseCliente = clienteService.listarClientesService(pageable);

        return ResponseEntity.ok(responseCliente);
    }

    // Controlador para buscar un cliente mediante el id
    @GetMapping("/buscarCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteByIdController(@PathVariable Integer id){
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarClienteById(id);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // Controlador para actualizar un cliente
    @PutMapping("/actualizarCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarClienteController(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO
    ){
        ClienteResponseDTO clienteResponseDTO = clienteService.actualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // Controlador para buscar cliente por cualquier campo
    @GetMapping("/filtrarCliente")
    public ResponseEntity<ClienteListarPageDTO> filtrarClienteController(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String apellidoPaterno,
            @RequestParam(required = false) String apellidoMaterno,
            @RequestParam(required = false) String telefono1,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCliente") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ClienteListarPageDTO clienteListarPageDTO = clienteService
                .buscarClienteFiltros(nombre, apellidoPaterno, apellidoMaterno, telefono1, pageable);

        return ResponseEntity.ok(clienteListarPageDTO);
    }
}