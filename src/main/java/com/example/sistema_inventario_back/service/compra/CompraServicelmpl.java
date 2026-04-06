package com.example.sistema_inventario_back.service.compra;

import com.example.sistema_inventario_back.dto.compra.*;
import com.example.sistema_inventario_back.entity.compra.Compra;
import com.example.sistema_inventario_back.entity.compra.CompraDetalle;
import com.example.sistema_inventario_back.entity.compra.MateriaPrima;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.entity.tela.CategoriaTela;
import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import com.example.sistema_inventario_back.entity.tela.Tela;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.compra.CompraRepository;
import com.example.sistema_inventario_back.repository.compra.MateriaPrimaRepository;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import com.example.sistema_inventario_back.service.compra.compra_interface.CompraService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CompraServicelmpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private UserRepository userRepository;

    // Servicio para obtener el detalle de la compra
    @Override
    public CompraDetalleDTO obtenerDetalleCompraService(Integer idCompra) {

        // Buscar la compra con todas sus relaciones
        Compra compra = compraRepository.findByIdWithDetails(idCompra)
                .orElseThrow(() -> new RuntimeException("Compra no encontrada con ID: " + idCompra));

        // Crear el DTO de respuesta
        CompraDetalleDTO dto = new CompraDetalleDTO();
        dto.setIdCompra(compra.getIdCompra());
        dto.setNumeroComprobante(compra.getNumeroComprobante());
        dto.setFechaCompra(compra.getFechaCompra());
        dto.setFechaActualizacion(compra.getFechaActualizacion());
        dto.setNotaEdicion(compra.getNotaEdicion());
        dto.setSubTotal(compra.getSubTotal());
        dto.setDescuento(compra.getDescuento());
        dto.setTotalCompra(compra.getTotalCompra());
        dto.setEstadoCompra(compra.getEstadoCompra() != null ? compra.getEstadoCompra().name() : "COMPLETADA");

        //Informacion proveedor
        Proveedor proveedor = compra.getProveedor();
        if (proveedor != null) {
            dto.setIdProveedor(proveedor.getIdProveedor());
            dto.setNombreComercial(proveedor.getNombreComercial());
            dto.setNombresEncargado(proveedor.getNombresEncargado());
            dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
            dto.setNumeroUno(proveedor.getNumeroUno());
            dto.setNumeroDos(proveedor.getNumeroDos());
            dto.setDireccion(proveedor.getDireccion());
            dto.setTipoProveedor(proveedor.getTipoProveedor() != null ? proveedor.getTipoProveedor().name() : null);
        }

        //Informacion del usuario
        Usuario usuario = compra.getUsuario();
        if (usuario != null) {
            dto.setIdUsuario(usuario.getId_usuario());
            // Concatenar nombre y apellido del usuario
            String nombreCompleto = usuario.getNombres();
            if (usuario.getApellidoPaterno() != null && !usuario.getApellidoPaterno().isEmpty()) {
                nombreCompleto += " " + usuario.getApellidoPaterno();
            }
            dto.setNombreUsuario(nombreCompleto);
        }

        // Detalles de la compra
        List<CompraItemDetalleDTO> items = new ArrayList<>();

        if (compra.getCompraDetalles() != null && !compra.getCompraDetalles().isEmpty()) {
            for (CompraDetalle detalle : compra.getCompraDetalles()) {
                CompraItemDetalleDTO itemDTO = mapearCompraDetalle(detalle);
                items.add(itemDTO);
            }
        }

        dto.setItems(items);
        dto.setCantidadItems(items.size());

        return dto;
    }

    // Servicio para obtener las estadisticas
    @Override
    public CompraEstadisticaDTO getEstadisticasService() {

        // Calcular fechas
        LocalDateTime ahora = LocalDateTime.now();
        LocalDateTime inicioMesActual = ahora.withDayOfMonth(1).withHour(0).withMinute(0).withSecond(0).withNano(0);
        LocalDateTime inicioMesAnterior = inicioMesActual.minusMonths(1);

        // Obtener datos
        Long totalCompras = compraRepository.countTotalCompras();
        Long comprasEsteMes = compraRepository.countComprasDelMes(inicioMesActual);
        BigDecimal gastoEsteMes = compraRepository.sumGastoDelMes(inicioMesActual);
        Long comprasMesAnterior = compraRepository.countComprasMesAnterior(inicioMesAnterior, inicioMesActual);
        BigDecimal gastoMesAnterior = compraRepository.sumGastoMesAnterior(inicioMesAnterior, inicioMesActual);

        // Obtener el proveedor
        String proveedorTop = "-";
        Long comprasProveedorTop = 0L;

        List<Object[]> proveedorData = compraRepository.findProveedorTop();
        if (proveedorData != null && !proveedorData.isEmpty()){
            Object[] resultado = proveedorData.get(0);
            proveedorTop = (String) resultado[0];
            comprasProveedorTop = (Long) resultado[1];
        }

        return new CompraEstadisticaDTO(
                totalCompras,
                comprasEsteMes,
                gastoEsteMes,
                proveedorTop,
                comprasProveedorTop,
                comprasMesAnterior,
                gastoMesAnterior
        );
    }

    // Servicio para listar a todas las compras
    @Override
    public CompraPageListarDTO getAllComprasPaginationService(Pageable pageable) {
        Page<CompraListarDTO> page = compraRepository.findAllComprasPagination(pageable);

        CompraPageListarDTO response = new CompraPageListarDTO();
        response.setListaCompra(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    // Servicio para crear una nueva compra
    @Override
    @Transactional
    public CompraResponseDTO createCompraService(CompraRequestDTO requestDTO, String username) {

        // Validar usuario
        Usuario usuario = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        Proveedor proveedor = proveedorRepository.findById(requestDTO.getIdProveedor())
                .orElseThrow(() -> new EntityNotFoundException("Proveedor no encontrado"));

        // Crear la compra
        Compra compra = new Compra();
        compra.setNumeroComprobante(requestDTO.getNumeroComprobante());
        compra.setNotaEdicion(requestDTO.getNotaEdicion());
        compra.setDescuento(requestDTO.getDescuento() != null ? requestDTO.getDescuento() : BigDecimal.ZERO);
        compra.setProveedor(proveedor);
        compra.setUsuario(usuario);

        // Procesar detalles y calcular subtotal
        BigDecimal subTotal = BigDecimal.ZERO;

        for (CompraDetalleRequestDTO detalleRequestDTO : requestDTO.getDetalles()) {
            CompraDetalle detalle = createDetalle(detalleRequestDTO, compra);
            compra.addDetalle(detalle);

            BigDecimal subTotalDetalle = detalleRequestDTO.getPrecioUnitario()
                    .multiply(BigDecimal.valueOf(detalleRequestDTO.getCantidadUnidadCompra()));
            subTotal = subTotal.add(subTotalDetalle);

            actualizarStock(detalleRequestDTO.getIdMateriaPrima(), detalleRequestDTO.getCantidadUnidadMedida());
        }

        compra.setSubTotal(subTotal);
        compra.setTotalCompra(subTotal.subtract(compra.getDescuento()));

        Compra compraSave = compraRepository.save(compra);

        return mapToResponseDTO(compraSave);
    }

    // Método para crear un detalle de compra
    private CompraDetalle createDetalle(CompraDetalleRequestDTO dto, Compra compra) {
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(dto.getIdMateriaPrima())
                .orElseThrow(() -> new EntityNotFoundException(
                        "Materia prima no encontrada con ID: " + dto.getIdMateriaPrima()));

        CompraDetalle detalle = new CompraDetalle();
        detalle.setCantidadUnidadCompra(dto.getCantidadUnidadCompra());
        detalle.setCantidadUnidadMedida(dto.getCantidadUnidadMedida());
        detalle.setUnidadCompra(dto.getUnidadCompra());
        detalle.setUnidadMedida(dto.getUnidadMedida());
        detalle.setPrecioUnitario(dto.getPrecioUnitario());
        detalle.setMateriaPrima(materiaPrima);
        detalle.setCompra(compra);

        return detalle;
    }

    // Método para actualizar stock de la materia prima
    private void actualizarStock(Integer idMateriaPrima, Double cantidadAgregar) {
        MateriaPrima materiaPrima = materiaPrimaRepository.findById(idMateriaPrima)
                .orElseThrow(() -> new EntityNotFoundException("Materia prima no encontrada"));

        Double nuevoStock = materiaPrima.getStockActual() + cantidadAgregar;
        materiaPrima.setStockActual(nuevoStock);
        materiaPrimaRepository.save(materiaPrima);
    }

    // Mapear a Response DTO
    private CompraResponseDTO mapToResponseDTO(Compra compra) {
        CompraResponseDTO response = new CompraResponseDTO();

        response.setIdCompra(compra.getIdCompra());
        response.setNumeroComprobante(compra.getNumeroComprobante());
        response.setSubTotal(compra.getSubTotal());
        response.setDescuento(compra.getDescuento());
        response.setTotalCompra(compra.getTotalCompra());
        response.setEstadoCompra(compra.getEstadoCompra());
        response.setNotaEdicion(compra.getNotaEdicion());
        response.setFechaCompra(compra.getFechaCompra());

        // Proveedor
        response.setIdProveedor(compra.getProveedor().getIdProveedor());
        response.setNombreProveedor(compra.getProveedor().getNombreComercial());

        // Usuario
        response.setUsuario(compra.getUsuario().getNombreUsuario());

        // Detalles
        List<CompraDetalleResponseDTO> detallesDTO = compra.getCompraDetalles()
                .stream()
                .map(this::mapDetalleToResponseDTO)
                .collect(Collectors.toList());
        response.setDetalles(detallesDTO);

        return response;
    }

    // Mapear detalle a Response DTO
    private CompraDetalleResponseDTO mapDetalleToResponseDTO(CompraDetalle detalle) {
        CompraDetalleResponseDTO dto = new CompraDetalleResponseDTO();

        dto.setIdCompraDetalle(detalle.getIdCompraDetalle());
        dto.setCantidadUnidadCompra(detalle.getCantidadUnidadCompra());
        dto.setCantidadUnidadMedida(detalle.getCantidadUnidadMedida());
        dto.setUnidadCompra(detalle.getUnidadCompra());
        dto.setUnidadMedida(detalle.getUnidadMedida());
        dto.setPrecioUnitario(detalle.getPrecioUnitario());
        dto.setSubTotalDetalle(detalle.getSubTotalDetalle());

        // Materia Prima
        dto.setIdMateriaPrima(detalle.getMateriaPrima().getIdMateriaPrima());
        dto.setNombreMateriaPrima(detalle.getMateriaPrima().getNombreMateriaPrima());

        return dto;
    }

    // Metodo para mapear un CompraDetalle a CompraDetalleDTO
    private CompraItemDetalleDTO mapearCompraDetalle(CompraDetalle detalle) {
        CompraItemDetalleDTO itemDTO = new CompraItemDetalleDTO();

        //Informacion del detalle
        itemDTO.setIdDetalleCompra(detalle.getIdCompraDetalle());
        itemDTO.setCantidadUnidadCompra(detalle.getCantidadUnidadCompra());
        itemDTO.setCantidadUnidadMedida(detalle.getCantidadUnidadMedida());
        itemDTO.setUnidadCompra(detalle.getUnidadCompra() != null ? detalle.getUnidadCompra().name() : null);
        itemDTO.setUnidadMedida(detalle.getUnidadMedida() != null ? detalle.getUnidadMedida().name() : null);
        itemDTO.setPrecioUnitario(detalle.getPrecioUnitario());
        itemDTO.setSubTotalDetalle(detalle.getSubTotalDetalle());

        // Materia Prima
        MateriaPrima mp = detalle.getMateriaPrima();
        if (mp != null) {
            itemDTO.setIdMateriaPrima(mp.getIdMateriaPrima());
            itemDTO.setNombreMateriaPrima(mp.getNombreMateriaPrima());
            itemDTO.setMarca(mp.getMarca());
            itemDTO.setModelo(mp.getModelo());
            itemDTO.setTipoMateriaPrima(mp.getTipoMateriaPrima() != null ? mp.getTipoMateriaPrima().name() : null);
            itemDTO.setUbicacionAlmacen(mp.getUbicacionAlmacen() != null ? mp.getUbicacionAlmacen().name() : null);

            // Verificar si es tela
            itemDTO.setEsTela(mp.esTela());

            // ═══════════════════════════════════════════════════════════
            // IMAGEN DE LA MATERIA PRIMA
            // ═══════════════════════════════════════════════════════════

            ImagenMateriaPrima imagen = mp.getImagenMateriaPrima();
            if (imagen != null) {
                itemDTO.setImagenUrl(imagen.getImagenUrl());
                itemDTO.setNombreImagen(imagen.getNombreImagen());
            }

            // ═══════════════════════════════════════════════════════════
            // INFORMACIÓN DE TELA (solo si es tela)
            // ═══════════════════════════════════════════════════════════

            if (mp.esTela() && mp.getTela() != null) {
                Tela tela = mp.getTela();

                itemDTO.setColor(tela.getColor());
                itemDTO.setCodigoTela(tela.getCodigoTela());

                // Información de la categoría de tela
                CategoriaTela categoria = tela.getCategoriaTela();
                if (categoria != null) {
                    itemDTO.setCodigoCategoria(categoria.getCodigoCategoria());
                    itemDTO.setComposicion(categoria.getComposicion());
                    itemDTO.setTituloCategoria(categoria.getTitulo());
                    itemDTO.setPeso(categoria.getPeso());
                    itemDTO.setAncho(categoria.getAncho());
                    itemDTO.setDensidad(categoria.getDensidad());
                    itemDTO.setLigamento(categoria.getLigamento());
                    itemDTO.setAcabado(categoria.getAcabado());
                }
            }
        }

        return itemDTO;
    }
}