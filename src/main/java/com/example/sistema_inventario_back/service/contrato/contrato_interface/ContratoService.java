package com.example.sistema_inventario_back.service.contrato.contrato_interface;

import com.example.sistema_inventario_back.dto.contrato.*;
import com.example.sistema_inventario_back.entity.contrato.EstadoContrato;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ContratoService {

    // ── CRUD CONTRATO ──────────────────────────────────────────────────────────

    /** Crear un nuevo contrato con sus ítems (camisas) */
    ContratoResponseDTO crearContrato(ContratoRequestDTO requestDTO);

    /** Listar todos los contratos paginados */
    ContratoListarPageDTO listarTodosContratos(Pageable pageable);

    /** Listar contratos activos (no cancelados) paginados */
    ContratoListarPageDTO listarContratosActivos(Pageable pageable);

    /** Obtener el detalle completo de un contrato por ID */
    ContratoResponseDTO buscarContratoPorId(Integer idContrato);

    /** Editar campos no sensibles de un contrato (fecha fin, descripción, estado) */
    ContratoResponseDTO editarContrato(Integer idContrato, ContratoEditarDTO editarDTO);

    /** Cancelar un contrato (eliminación lógica) */
    ContratoResponseDTO cancelarContrato(Integer idContrato, ContratoCancelarDTO cancelarDTO);

    // ── FILTROS ────────────────────────────────────────────────────────────────

    /** Filtrar contratos por cualquier campo */
    ContratoListarPageDTO filtrarContratos(
            String codigoContrato,
            String clienteNombre,
            EstadoContrato estadoContrato,
            String fechaInicio,
            String fechaFin,
            Pageable pageable
    );

    // ── CONTRATOS POR CLIENTE ──────────────────────────────────────────────────

    /** Obtener todos los contratos de un cliente (incluidos finalizados y cancelados) */
    List<ContratoResponseDTO> obtenerContratosPorCliente(Integer idCliente);

    // ── ESTADO DE PRODUCTO (CAMISAS) ───────────────────────────────────────────

    /** Actualizar el estado de fabricación de una camisa específica en el contrato */
    ContratoDetalleResponseDTO actualizarEstadoProducto(Integer idDetalle, ContratoEstadoProductoDTO estadoProductoDTO);

    // ── PAGOS ──────────────────────────────────────────────────────────────────

    /** Registrar un pago para un contrato */
    PagoContratoResponseDTO registrarPago(Integer idContrato, PagoContratoRequestDTO pagoDTO);

    /** Obtener todos los pagos de un contrato */
    List<PagoContratoResponseDTO> listarPagosPorContrato(Integer idContrato);

    // ── RESUMEN / ESTADÍSTICAS ─────────────────────────────────────────────────

    /** Obtener un resumen del contrato: monto total, pagado, saldo pendiente */
    ContratoResumenDTO obtenerResumenContrato(Integer idContrato);
}
