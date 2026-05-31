package com.example.sistema_inventario_back.controller.cliente;

import com.example.sistema_inventario_back.dto.cliente.ClienteDesactivarDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import com.example.sistema_inventario_back.dto.contrato.ContratoResponseDTO;
import com.example.sistema_inventario_back.service.cliente.cliente_interface.ClienteService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api/cliente")
public class ClienteController {

    @Autowired
    private ClienteService clienteService;

    // ── POST: Crear un nuevo cliente ───────────────────────────────────────────
    @PostMapping("/crearCliente")
    public ResponseEntity<ClienteResponseDTO> crearClienteController(
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.crearClienteService(clienteRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteResponseDTO);
    }

    // ── GET: Listar clientes activos (paginado) ────────────────────────────────
    @GetMapping("/listarClientesActivos")
    public ResponseEntity<ClienteListarPageDTO> listarClientesController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCliente") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);
        ClienteListarPageDTO responseCliente = clienteService.listarClientesService(pageable);

        return ResponseEntity.ok(responseCliente);
    }

    // ── GET: Buscar un cliente por ID ──────────────────────────────────────────
    @GetMapping("/buscarCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> buscarClienteByIdController(@PathVariable Integer id) {
        ClienteResponseDTO clienteResponseDTO = clienteService.buscarClienteById(id);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // ── PUT: Actualizar datos de un cliente ────────────────────────────────────
    @PutMapping("/actualizarCliente/{id}")
    public ResponseEntity<ClienteResponseDTO> actualizarClienteController(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.actualizarCliente(id, clienteRequestDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // ── GET: Filtrar clientes por cualquier campo ──────────────────────────────
    @GetMapping("/filtrarCliente")
    public ResponseEntity<ClienteListarPageDTO> filtrarClienteController(
            @RequestParam(required = false) String nombres,
            @RequestParam(required = false) String apellidoPaterno,
            @RequestParam(required = false) String apellidoMaterno,
            @RequestParam(required = false) String telefonoUno,
            @RequestParam(required = false) String carnetIdentidad,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCliente") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        ClienteListarPageDTO clienteListarPageDTO = clienteService
                .buscarClienteFiltros(nombres, apellidoPaterno, apellidoMaterno, telefonoUno, carnetIdentidad, pageable);

        return ResponseEntity.ok(clienteListarPageDTO);
    }

    // ── PATCH: Desactivar (eliminación lógica) un cliente ─────────────────────
    @PatchMapping("/desactivar/{id}")
    public ResponseEntity<ClienteResponseDTO> desactivarClienteController(
            @PathVariable Integer id,
            @Valid @RequestBody ClienteDesactivarDTO desactivarDTO) {
        ClienteResponseDTO clienteResponseDTO = clienteService.desactivarCliente(id, desactivarDTO);
        return ResponseEntity.ok(clienteResponseDTO);
    }

    // ── GET: Obtener todos los contratos de un cliente ─────────────────────────
    @GetMapping("/{id}/contratos")
    public ResponseEntity<List<ContratoResponseDTO>> obtenerContratosDelClienteController(
            @PathVariable Integer id) {
        List<ContratoResponseDTO> contratos = clienteService.obtenerContratosDelCliente(id);
        return ResponseEntity.ok(contratos);
    }
}