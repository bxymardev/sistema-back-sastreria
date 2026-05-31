package com.example.sistema_inventario_back.controller.contrato;

import com.example.sistema_inventario_back.dto.contrato.*;
import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import com.example.sistema_inventario_back.service.contrato.contrato_interface.ContratoService;
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
@RequestMapping("/api/contrato")
public class ContratoController {

    @Autowired
    private ContratoService contratoService;

    // ── POST: Crear un nuevo contrato ──────────────────────────────────────────
    @PostMapping("/crearContrato")
    public ResponseEntity<ContratoResponseDTO> crearContrato(
            @Valid @RequestBody ContratoRequestDTO requestDTO) {
        ContratoResponseDTO response = contratoService.crearContrato(requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // ── GET: Listar todos los contratos (paginado) ─────────────────────────────
    @GetMapping("/listarTodos")
    public ResponseEntity<ContratoListarPageDTO> listarTodosContratos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), sort);

        return ResponseEntity.ok(contratoService.listarTodosContratos(pageable));
    }

    // ── GET: Listar contratos activos (no cancelados, paginado) ────────────────
    @GetMapping("/listarActivos")
    public ResponseEntity<ContratoListarPageDTO> listarContratosActivos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), sort);

        return ResponseEntity.ok(contratoService.listarContratosActivos(pageable));
    }

    // ── GET: Buscar un contrato por ID ─────────────────────────────────────────
    @GetMapping("/buscar/{id}")
    public ResponseEntity<ContratoResponseDTO> buscarContratoPorId(@PathVariable Integer id) {
        return ResponseEntity.ok(contratoService.buscarContratoPorId(id));
    }

    // ── PUT: Editar campos no sensibles del contrato ───────────────────────────
    @PutMapping("/editar/{id}")
    public ResponseEntity<ContratoResponseDTO> editarContrato(
            @PathVariable Integer id,
            @Valid @RequestBody ContratoEditarDTO editarDTO) {
        return ResponseEntity.ok(contratoService.editarContrato(id, editarDTO));
    }

    // ── PATCH: Cancelar un contrato (eliminación lógica) ──────────────────────
    @PatchMapping("/cancelar/{id}")
    public ResponseEntity<ContratoResponseDTO> cancelarContrato(
            @PathVariable Integer id,
            @Valid @RequestBody ContratoCancelarDTO cancelarDTO) {
        return ResponseEntity.ok(contratoService.cancelarContrato(id, cancelarDTO));
    }

    // ── GET: Filtrar contratos por múltiples parámetros ────────────────────────
    @GetMapping("/filtrar")
    public ResponseEntity<ContratoListarPageDTO> filtrarContratos(
            @RequestParam(required = false) String codigoContrato,
            @RequestParam(required = false) String clienteNombre,
            @RequestParam(required = false) EstadoContrato estadoContrato,
            @RequestParam(required = false) String fechaInicio,
            @RequestParam(required = false) String fechaFin,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "fechaCreacion") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase("asc")
                ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(Math.max(page, 0), Math.min(Math.max(size, 1), 100), sort);

        return ResponseEntity.ok(contratoService.filtrarContratos(
                codigoContrato, clienteNombre, estadoContrato, fechaInicio, fechaFin, pageable));
    }

    // ── GET: Obtener todos los contratos de un cliente ─────────────────────────
    @GetMapping("/porCliente/{idCliente}")
    public ResponseEntity<List<ContratoResponseDTO>> obtenerContratosPorCliente(
            @PathVariable Integer idCliente) {
        return ResponseEntity.ok(contratoService.obtenerContratosPorCliente(idCliente));
    }

    // ── PATCH: Actualizar estado de fabricación de una camisa ──────────────────
    @PatchMapping("/detalle/{idDetalle}/estado")
    public ResponseEntity<ContratoDetalleResponseDTO> actualizarEstadoProducto(
            @PathVariable Integer idDetalle,
            @Valid @RequestBody ContratoEstadoProductoDTO estadoProductoDTO) {
        return ResponseEntity.ok(contratoService.actualizarEstadoProducto(idDetalle, estadoProductoDTO));
    }

    // ── POST: Registrar un pago para un contrato ───────────────────────────────
    @PostMapping("/{idContrato}/pago")
    public ResponseEntity<PagoContratoResponseDTO> registrarPago(
            @PathVariable Integer idContrato,
            @Valid @RequestBody PagoContratoRequestDTO pagoDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(contratoService.registrarPago(idContrato, pagoDTO));
    }

    // ── GET: Listar todos los pagos de un contrato ─────────────────────────────
    @GetMapping("/{idContrato}/pagos")
    public ResponseEntity<List<PagoContratoResponseDTO>> listarPagosPorContrato(
            @PathVariable Integer idContrato) {
        return ResponseEntity.ok(contratoService.listarPagosPorContrato(idContrato));
    }

    // ── GET: Resumen financiero y de producción del contrato ───────────────────
    @GetMapping("/{idContrato}/resumen")
    public ResponseEntity<ContratoResumenDTO> obtenerResumenContrato(
            @PathVariable Integer idContrato) {
        return ResponseEntity.ok(contratoService.obtenerResumenContrato(idContrato));
    }
}
