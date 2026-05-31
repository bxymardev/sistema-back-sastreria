package com.example.sistema_inventario_back.service.contrato;

import com.example.sistema_inventario_back.dto.contrato.*;
import com.example.sistema_inventario_back.entity.cliente.Cliente;
import com.example.sistema_inventario_back.entity.contrato.*;
import com.example.sistema_inventario_back.repository.cliente.ClienteRepository;
import com.example.sistema_inventario_back.repository.contrato.ContratoDetalleRepository;
import com.example.sistema_inventario_back.repository.contrato.ContratoRepository;
import com.example.sistema_inventario_back.repository.contrato.PagoContratoRepository;
import com.example.sistema_inventario_back.service.contrato.contrato_interface.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class ContratoServiceImpl implements ContratoService {

    @Autowired
    private ContratoRepository contratoRepository;

    @Autowired
    private ContratoDetalleRepository contratoDetalleRepository;

    @Autowired
    private PagoContratoRepository pagoContratoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // ─── CREAR CONTRATO ────────────────────────────────────────────────────────

    @Override
    @Transactional
    public ContratoResponseDTO crearContrato(ContratoRequestDTO requestDTO) {
        Cliente cliente = clienteRepository.findById(requestDTO.getIdCliente())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + requestDTO.getIdCliente()));

        if (!cliente.getEstadoCliente()) {
            throw new RuntimeException("No se puede crear un contrato para un cliente inactivo.");
        }

        if (requestDTO.getMontoAdelantado().compareTo(requestDTO.getMontoTotal()) > 0) {
            throw new RuntimeException("El monto adelantado no puede ser mayor al monto total del contrato.");
        }

        Contrato contrato = new Contrato();
        contrato.setCliente(cliente);

        // Generar código automático si no se provee
        if (requestDTO.getCodigoContrato() != null && !requestDTO.getCodigoContrato().isBlank()) {
            if (contratoRepository.existsByCodigoContrato(requestDTO.getCodigoContrato())) {
                throw new RuntimeException("Ya existe un contrato con el código: " + requestDTO.getCodigoContrato());
            }
            contrato.setCodigoContrato(requestDTO.getCodigoContrato().toUpperCase());
        } else {
            contrato.setCodigoContrato(generarCodigoContrato());
        }

        contrato.setMontoTotal(requestDTO.getMontoTotal());
        contrato.setMontoAdelantado(requestDTO.getMontoAdelantado());
        contrato.setTipoContrato(requestDTO.getTipoContrato());
        contrato.setFechaInicio(requestDTO.getFechaInicio());
        contrato.setFechaFin(requestDTO.getFechaFin());
        contrato.setDescripcion(requestDTO.getDescripcion());
        contrato.setEstadoContrato(EstadoContrato.ACTIVO);

        // Agregar los detalles (camisas)
        for (ContratoDetalleRequestDTO detalleDTO : requestDTO.getDetalles()) {
            ContratoDetalle detalle = mapDetalleRequestToEntity(detalleDTO);
            contrato.addDetalle(detalle);
        }

        Contrato contratoGuardado = contratoRepository.save(contrato);
        return mapContratoToResponse(contratoGuardado);
    }

    // ─── LISTAR TODOS ──────────────────────────────────────────────────────────

    @Override
    public ContratoListarPageDTO listarTodosContratos(Pageable pageable) {
        Page<Contrato> pagina = contratoRepository.listarTodosLosContratos(pageable);
        return buildListarPageDTO(pagina);
    }

    @Override
    public ContratoListarPageDTO listarContratosActivos(Pageable pageable) {
        Page<Contrato> pagina = contratoRepository.listarContratosActivos(pageable);
        return buildListarPageDTO(pagina);
    }

    // ─── BUSCAR POR ID ─────────────────────────────────────────────────────────

    @Override
    public ContratoResponseDTO buscarContratoPorId(Integer idContrato) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));
        return mapContratoToResponse(contrato);
    }

    // ─── EDITAR CAMPOS NO SENSIBLES ────────────────────────────────────────────

    @Override
    @Transactional
    public ContratoResponseDTO editarContrato(Integer idContrato, ContratoEditarDTO editarDTO) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));

        if (contrato.getEstadoContrato() == EstadoContrato.CANCELADO) {
            throw new RuntimeException("No se puede editar un contrato cancelado.");
        }

        if (editarDTO.getFechaFin() != null) {
            contrato.setFechaFin(editarDTO.getFechaFin());
        }
        if (editarDTO.getDescripcion() != null) {
            contrato.setDescripcion(editarDTO.getDescripcion());
        }
        if (editarDTO.getEstadoContrato() != null) {
            // Validar transición de estado válida
            validarTransicionEstado(contrato.getEstadoContrato(), editarDTO.getEstadoContrato());
            contrato.setEstadoContrato(editarDTO.getEstadoContrato());
        }

        Contrato actualizado = contratoRepository.save(contrato);
        return mapContratoToResponse(actualizado);
    }

    // ─── CANCELAR (ELIMINACIÓN LÓGICA) ────────────────────────────────────────

    @Override
    @Transactional
    public ContratoResponseDTO cancelarContrato(Integer idContrato, ContratoCancelarDTO cancelarDTO) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));

        if (contrato.getEstadoContrato() == EstadoContrato.CANCELADO) {
            throw new RuntimeException("El contrato ya se encuentra cancelado.");
        }

        if (contrato.getEstadoContrato() == EstadoContrato.FINALIZADO) {
            throw new RuntimeException("No se puede cancelar un contrato ya finalizado.");
        }

        contrato.cancelar(cancelarDTO.getMotivo());
        Contrato cancelado = contratoRepository.save(contrato);
        return mapContratoToResponse(cancelado);
    }

    // ─── FILTROS ────────────────────────────────────────────────────────────────

    @Override
    public ContratoListarPageDTO filtrarContratos(
            String codigoContrato,
            String clienteNombre,
            EstadoContrato estadoContrato,
            String fechaInicioStr,
            String fechaFinStr,
            Pageable pageable) {

        Specification<Contrato> spec = Specification.where(null);

        if (codigoContrato != null && !codigoContrato.isBlank()) {
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("codigoContrato")), "%" + codigoContrato.toLowerCase() + "%"));
        }

        if (clienteNombre != null && !clienteNombre.isBlank()) {
            String pattern = "%" + clienteNombre.toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> cb.or(
                    cb.like(cb.lower(root.get("cliente").get("nombres")), pattern),
                    cb.like(cb.lower(root.get("cliente").get("apellidoPaterno")), pattern),
                    cb.like(cb.lower(root.get("cliente").get("apellidoMaterno")), pattern)
            ));
        }

        if (estadoContrato != null) {
            spec = spec.and((root, query, cb) ->
                    cb.equal(root.get("estadoContrato"), estadoContrato));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if (fechaInicioStr != null && !fechaInicioStr.isBlank()) {
            LocalDate fechaInicio = LocalDate.parse(fechaInicioStr, formatter);
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("fechaInicio"), fechaInicio));
        }

        if (fechaFinStr != null && !fechaFinStr.isBlank()) {
            LocalDate fechaFin = LocalDate.parse(fechaFinStr, formatter);
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("fechaFin"), fechaFin));
        }

        Page<Contrato> pagina = contratoRepository.findAll(spec, pageable);
        return buildListarPageDTO(pagina);
    }

    // ─── CONTRATOS POR CLIENTE ─────────────────────────────────────────────────

    @Override
    public List<ContratoResponseDTO> obtenerContratosPorCliente(Integer idCliente) {
        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + idCliente));

        List<Contrato> contratos = contratoRepository.findByClienteId(idCliente);
        return contratos.stream().map(this::mapContratoToResponse).toList();
    }

    // ─── ESTADO DE PRODUCTO (CAMISAS) ─────────────────────────────────────────

    @Override
    @Transactional
    public ContratoDetalleResponseDTO actualizarEstadoProducto(Integer idDetalle, ContratoEstadoProductoDTO estadoProductoDTO) {
        ContratoDetalle detalle = contratoDetalleRepository.findById(idDetalle)
                .orElseThrow(() -> new RuntimeException("Detalle de contrato no encontrado con id: " + idDetalle));

        if (detalle.getContrato().getEstadoContrato() == EstadoContrato.CANCELADO) {
            throw new RuntimeException("No se puede actualizar el estado de un producto en un contrato cancelado.");
        }

        detalle.setEstadoProducto(estadoProductoDTO.getEstadoProducto());
        ContratoDetalle actualizado = contratoDetalleRepository.save(detalle);

        // Verificar si todos los productos están ENTREGADOS para finalizar el contrato automáticamente
        verificarYFinalizarContrato(detalle.getContrato());

        return mapDetalleToResponse(actualizado);
    }

    // ─── PAGOS ────────────────────────────────────────────────────────────────

    @Override
    @Transactional
    public PagoContratoResponseDTO registrarPago(Integer idContrato, PagoContratoRequestDTO pagoDTO) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));

        if (contrato.getEstadoContrato() == EstadoContrato.CANCELADO) {
            throw new RuntimeException("No se puede registrar un pago en un contrato cancelado.");
        }

        // Calcular saldo actual basado en pagos previos
        Double totalPagado = pagoContratoRepository.sumPagosByContrato(idContrato);
        BigDecimal totalPagadoBD = BigDecimal.valueOf(totalPagado != null ? totalPagado : 0.0);
        BigDecimal saldoActual = contrato.getMontoTotal().subtract(totalPagadoBD);

        if (pagoDTO.getMonto().compareTo(saldoActual) > 0) {
            throw new RuntimeException(String.format(
                    "El monto del pago (%.2f) excede el saldo pendiente (%.2f).",
                    pagoDTO.getMonto(), saldoActual));
        }

        PagoContrato pago = new PagoContrato(
                pagoDTO.getMonto().doubleValue(),
                pagoDTO.getMetodoPago(),
                EstadoPagoContrato.COMPLETO,
                pagoDTO.getFechaPago()
        );
        pago.setContrato(contrato);
        pago.setObservaciones(pagoDTO.getObservaciones());

        PagoContrato pagoGuardado = pagoContratoRepository.save(pago);

        // Actualizar el saldo del contrato
        BigDecimal nuevoSaldo = saldoActual.subtract(pagoDTO.getMonto());
        contrato.setSaldo(nuevoSaldo);
        contratoRepository.save(contrato);

        return mapPagoToResponse(pagoGuardado);
    }

    @Override
    public List<PagoContratoResponseDTO> listarPagosPorContrato(Integer idContrato) {
        contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));

        List<PagoContrato> pagos = pagoContratoRepository.findByContrato_IdContrato(idContrato);
        return pagos.stream().map(this::mapPagoToResponse).toList();
    }

    // ─── RESUMEN ──────────────────────────────────────────────────────────────

    @Override
    public ContratoResumenDTO obtenerResumenContrato(Integer idContrato) {
        Contrato contrato = contratoRepository.findById(idContrato)
                .orElseThrow(() -> new RuntimeException("Contrato no encontrado con id: " + idContrato));

        Double totalPagadoRaw = pagoContratoRepository.sumPagosByContrato(idContrato);
        BigDecimal totalPagado = BigDecimal.valueOf(totalPagadoRaw != null ? totalPagadoRaw : 0.0);
        BigDecimal saldoPendiente = contrato.getMontoTotal().subtract(totalPagado);

        long prendasListas = contrato.getContratoDetalles().stream()
                .filter(d -> d.getEstadoProducto() == EstadoProducto.LISTO
                        || d.getEstadoProducto() == EstadoProducto.ENTREGADO)
                .count();

        long prendasEntregadas = contrato.getContratoDetalles().stream()
                .filter(d -> d.getEstadoProducto() == EstadoProducto.ENTREGADO)
                .count();

        int totalPrendas = contrato.getContratoDetalles().stream()
                .mapToInt(ContratoDetalle::getCantidad)
                .sum();

        int prendasListasTotal = contrato.getContratoDetalles().stream()
                .filter(d -> d.getEstadoProducto() == EstadoProducto.LISTO
                        || d.getEstadoProducto() == EstadoProducto.ENTREGADO)
                .mapToInt(ContratoDetalle::getCantidad)
                .sum();

        int prendasEntregadasTotal = contrato.getContratoDetalles().stream()
                .filter(d -> d.getEstadoProducto() == EstadoProducto.ENTREGADO)
                .mapToInt(ContratoDetalle::getCantidad)
                .sum();

        ContratoResumenDTO resumen = new ContratoResumenDTO();
        resumen.setIdContrato(contrato.getIdContrato());
        resumen.setCodigoContrato(contrato.getCodigoContrato());
        resumen.setEstadoContrato(contrato.getEstadoContrato());
        resumen.setMontoTotal(contrato.getMontoTotal());
        resumen.setMontoAdelantado(contrato.getMontoAdelantado());
        resumen.setTotalPagado(totalPagado);
        resumen.setSaldoPendiente(saldoPendiente.compareTo(BigDecimal.ZERO) < 0 ? BigDecimal.ZERO : saldoPendiente);
        resumen.setTotalPrendas(totalPrendas);
        resumen.setPrendasListas(prendasListasTotal);
        resumen.setPrendasEntregadas(prendasEntregadasTotal);
        resumen.setContratoCompletado(saldoPendiente.compareTo(BigDecimal.ZERO) <= 0
                && prendasEntregadas == contrato.getContratoDetalles().size());

        return resumen;
    }

    // ─── Helpers privados ─────────────────────────────────────────────────────

    private String generarCodigoContrato() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmm"));
        String uid = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
        String codigo = "CONT-" + timestamp + "-" + uid;

        // Garantizar unicidad
        int intentos = 0;
        while (contratoRepository.existsByCodigoContrato(codigo) && intentos < 5) {
            uid = UUID.randomUUID().toString().substring(0, 4).toUpperCase();
            codigo = "CONT-" + timestamp + "-" + uid;
            intentos++;
        }
        return codigo;
    }

    private void validarTransicionEstado(EstadoContrato actual, EstadoContrato nuevo) {
        if (actual == EstadoContrato.CANCELADO) {
            throw new RuntimeException("Un contrato cancelado no puede cambiar de estado.");
        }
        if (actual == EstadoContrato.FINALIZADO && nuevo == EstadoContrato.ACTIVO) {
            throw new RuntimeException("Un contrato finalizado no puede volver a ACTIVO.");
        }
    }

    private void verificarYFinalizarContrato(Contrato contrato) {
        boolean todosEntregados = contrato.getContratoDetalles().stream()
                .allMatch(d -> d.getEstadoProducto() == EstadoProducto.ENTREGADO);

        if (todosEntregados && contrato.getEstadoContrato() == EstadoContrato.ACTIVO) {
            contrato.setEstadoContrato(EstadoContrato.FINALIZADO);
            contratoRepository.save(contrato);
        }
    }

    private ContratoListarPageDTO buildListarPageDTO(Page<Contrato> pagina) {
        List<ContratoListarDTO> lista = pagina.getContent().stream()
                .map(this::mapContratoToListarDTO)
                .toList();

        ContratoListarPageDTO pageDTO = new ContratoListarPageDTO();
        pageDTO.setContratos(lista);
        pageDTO.setPaginaActual(pagina.getNumber());
        pageDTO.setTotalPaginas(pagina.getTotalPages());
        pageDTO.setTotalElementos(pagina.getTotalElements());
        pageDTO.setTamanioPagina(pagina.getSize());
        return pageDTO;
    }

    // ─── Mapeos ───────────────────────────────────────────────────────────────

    private ContratoListarDTO mapContratoToListarDTO(Contrato contrato) {
        ContratoListarDTO dto = new ContratoListarDTO();
        dto.setIdContrato(contrato.getIdContrato());
        dto.setCodigoContrato(contrato.getCodigoContrato());
        dto.setClienteNombreCompleto(
                contrato.getCliente().getNombres() + " "
                        + contrato.getCliente().getApellidoPaterno() + " "
                        + contrato.getCliente().getApellidoMaterno()
        );
        dto.setMontoTotal(contrato.getMontoTotal());
        dto.setSaldo(contrato.getSaldo());
        dto.setEstadoContrato(contrato.getEstadoContrato());
        dto.setTipoContrato(contrato.getTipoContrato());
        dto.setFechaInicio(contrato.getFechaInicio());
        dto.setFechaFin(contrato.getFechaFin());
        dto.setFechaCreacion(contrato.getFechaCreacion());
        return dto;
    }

    private ContratoResponseDTO mapContratoToResponse(Contrato contrato) {
        ContratoResponseDTO dto = new ContratoResponseDTO();
        dto.setIdContrato(contrato.getIdContrato());
        dto.setCodigoContrato(contrato.getCodigoContrato());
        dto.setMontoTotal(contrato.getMontoTotal());
        dto.setMontoAdelantado(contrato.getMontoAdelantado());
        dto.setSaldo(contrato.getSaldo());
        dto.setEstadoContrato(contrato.getEstadoContrato());
        dto.setTipoContrato(contrato.getTipoContrato());
        dto.setFechaInicio(contrato.getFechaInicio());
        dto.setFechaFin(contrato.getFechaFin());
        dto.setFechaCreacion(contrato.getFechaCreacion());
        dto.setFechaActualizacion(contrato.getFechaActualizacion());
        dto.setDescripcion(contrato.getDescripcion());
        dto.setMotivoCancelacion(contrato.getMotivoCancelacion());
        dto.setFechaCancelacion(contrato.getFechaCancelacion());
        dto.setIdCliente(contrato.getCliente().getIdCliente());
        dto.setClienteNombresCompleto(
                contrato.getCliente().getNombres() + " "
                        + contrato.getCliente().getApellidoPaterno() + " "
                        + contrato.getCliente().getApellidoMaterno()
        );
        dto.setClienteTelefonoUno(contrato.getCliente().getTelefonoUno());

        List<ContratoDetalleResponseDTO> detallesDTO = contrato.getContratoDetalles()
                .stream()
                .map(this::mapDetalleToResponse)
                .toList();
        dto.setDetalles(detallesDTO);
        return dto;
    }

    private ContratoDetalleResponseDTO mapDetalleToResponse(ContratoDetalle detalle) {
        ContratoDetalleResponseDTO dto = new ContratoDetalleResponseDTO();
        dto.setIdContratoDetalle(detalle.getIdContratoDetalle());
        dto.setDescripcionProducto(detalle.getDescripcionProducto());
        dto.setCantidad(detalle.getCantidad());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubTotal(detalle.getSubTotal());
        dto.setTalla(detalle.getTalla());
        dto.setColor(detalle.getColor());
        dto.setObservaciones(detalle.getObservaciones());
        dto.setEstadoProducto(detalle.getEstadoProducto());
        return dto;
    }

    private ContratoDetalle mapDetalleRequestToEntity(ContratoDetalleRequestDTO dto) {
        ContratoDetalle detalle = new ContratoDetalle();
        detalle.setDescripcionProducto(dto.getDescripcionProducto());
        detalle.setCantidad(dto.getCantidad());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setTalla(dto.getTalla());
        detalle.setColor(dto.getColor());
        detalle.setObservaciones(dto.getObservaciones());
        detalle.setEstadoProducto(
                dto.getEstadoProducto() != null ? dto.getEstadoProducto() : EstadoProducto.PENDIENTE
        );
        return detalle;
    }

    private PagoContratoResponseDTO mapPagoToResponse(PagoContrato pago) {
        PagoContratoResponseDTO dto = new PagoContratoResponseDTO();
        dto.setIdPagoContrato(pago.getIdPagoContrato());
        dto.setMonto(BigDecimal.valueOf(pago.getMontoTotal()));
        dto.setMetodoPago(pago.getMetodoPago());
        dto.setEstadoPagoContrato(pago.getEstadoPagoContrato());
        dto.setFechaPago(pago.getFechaPago());
        dto.setObservaciones(pago.getObservaciones());
        dto.setIdContrato(pago.getContrato().getIdContrato());
        dto.setCodigoContrato(pago.getContrato().getCodigoContrato());
        return dto;
    }
}
