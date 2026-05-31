package com.example.sistema_inventario_back.service.cliente;

import com.example.sistema_inventario_back.dto.cliente.ClienteDesactivarDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteListarDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import com.example.sistema_inventario_back.dto.contrato.ContratoDetalleResponseDTO;
import com.example.sistema_inventario_back.dto.contrato.ContratoResponseDTO;
import com.example.sistema_inventario_back.entity.cliente.Cliente;
import com.example.sistema_inventario_back.entity.contrato.Contrato;
import com.example.sistema_inventario_back.entity.contrato.ContratoDetalle;
import com.example.sistema_inventario_back.repository.cliente.ClienteRepository;
import com.example.sistema_inventario_back.repository.contrato.ContratoRepository;
import com.example.sistema_inventario_back.service.cliente.cliente_interface.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ClienteServicelmpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ContratoRepository contratoRepository;

    // Servicio para crear un cliente
    @Override
    @Transactional
    public ClienteResponseDTO crearClienteService(ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = matToEntity(clienteRequestDTO);
        Cliente clienteGuardar = clienteRepository.save(cliente);
        return matToResponseDTO(clienteGuardar);
    }

    // Servicio para listar a todos los clientes activos
    @Override
    public ClienteListarPageDTO listarClientesService(Pageable pageable) {
        Page<ClienteListarDTO> pageClientes = clienteRepository.listarClientesActivos(pageable);

        ClienteListarPageDTO clienteListarPageDTO = new ClienteListarPageDTO();
        clienteListarPageDTO.setClientes(pageClientes.getContent());
        clienteListarPageDTO.setPaginaActual(pageClientes.getNumber());
        clienteListarPageDTO.setTotalElementos(pageClientes.getTotalElements());
        clienteListarPageDTO.setTotalPaginas(pageClientes.getTotalPages());
        clienteListarPageDTO.setTamanioPagina(pageClientes.getSize());

        return clienteListarPageDTO;
    }

    // Servicio para buscar un cliente mediante el id
    @Override
    public ClienteResponseDTO buscarClienteById(Integer id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));
        return matToResponseDTO(cliente);
    }

    // Servicio para actualizar un cliente
    @Override
    @Transactional
    public ClienteResponseDTO actualizarCliente(Integer id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        if (!cliente.getEstadoCliente()) {
            throw new RuntimeException("No se puede actualizar un cliente inactivo.");
        }

        cliente.setNombres(clienteRequestDTO.getNombres().toUpperCase());
        cliente.setApellidoPaterno(clienteRequestDTO.getApellidoPaterno().toUpperCase());
        cliente.setApellidoMaterno(clienteRequestDTO.getApellidoMaterno().toUpperCase());
        cliente.setCarnetIdentidad(clienteRequestDTO.getCarnetIdentidad());
        cliente.setTelefonoUno(clienteRequestDTO.getTelefonoUno());
        cliente.setTelefonoDos(clienteRequestDTO.getTelefonoDos());

        Cliente clienteActualizado = clienteRepository.save(cliente);
        return matToResponseDTO(clienteActualizado);
    }

    // Servicio para buscar cliente por cualquier campo (filtros)
    @Override
    public ClienteListarPageDTO buscarClienteFiltros(
            String nombres,
            String apellidoPaterno,
            String apellidoMaterno,
            String telefonoUno,
            String carnetIdentidad,
            Pageable pageable) {

        Specification<Cliente> specification = Specification.where(null);

        if (nombres != null && !nombres.isBlank()) {
            specification = specification
                    .or((root, query, cb) -> cb.like(cb.lower(root.get("nombres")), "%" + nombres.toLowerCase() + "%"));
        }

        if (apellidoPaterno != null && !apellidoPaterno.isBlank()) {
            specification = specification
                    .or((root, query, cb) -> cb.like(cb.lower(root.get("apellidoPaterno")), "%" + apellidoPaterno.toLowerCase() + "%"));
        }

        if (apellidoMaterno != null && !apellidoMaterno.isBlank()) {
            specification = specification
                    .or((root, query, cb) -> cb.like(cb.lower(root.get("apellidoMaterno")), "%" + apellidoMaterno.toLowerCase() + "%"));
        }

        if (telefonoUno != null && !telefonoUno.isBlank()) {
            specification = specification
                    .or((root, query, cb) -> cb.like(cb.lower(root.get("telefonoUno")), "%" + telefonoUno.toLowerCase() + "%"));
        }

        if (carnetIdentidad != null && !carnetIdentidad.isBlank()) {
            specification = specification
                    .or((root, query, cb) -> cb.like(cb.lower(root.get("carnetIdentidad")), "%" + carnetIdentidad.toLowerCase() + "%"));
        }

        Page<Cliente> clientePage = clienteRepository.findAll(specification, pageable);

        List<ClienteListarDTO> listaClientes = clientePage
                .getContent()
                .stream()
                .map(this::matToListarDTO)
                .toList();

        ClienteListarPageDTO clienteListarPageDTO = new ClienteListarPageDTO();
        clienteListarPageDTO.setClientes(listaClientes);
        clienteListarPageDTO.setPaginaActual(clientePage.getNumber());
        clienteListarPageDTO.setTotalPaginas(clientePage.getTotalPages());
        clienteListarPageDTO.setTotalElementos(clientePage.getTotalElements());
        clienteListarPageDTO.setTamanioPagina(clientePage.getSize());

        return clienteListarPageDTO;
    }

    // Desactivar (eliminación lógica) un cliente
    @Override
    @Transactional
    public ClienteResponseDTO desactivarCliente(Integer id, ClienteDesactivarDTO desactivarDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + id));

        if (!cliente.getEstadoCliente()) {
            throw new RuntimeException("El cliente ya se encuentra inactivo.");
        }

        // Verificar que no tenga contratos activos
        long contratosActivos = contratoRepository.countContratosActivosByCliente(id);
        if (contratosActivos > 0) {
            throw new RuntimeException("No se puede desactivar el cliente porque tiene " + contratosActivos
                    + " contrato(s) activo(s). Cancele los contratos primero.");
        }

        cliente.desactivar(desactivarDTO.getMotivo());
        Cliente clienteDesactivado = clienteRepository.save(cliente);
        return matToResponseDTO(clienteDesactivado);
    }

    // Obtener todos los contratos de un cliente
    @Override
    public List<ContratoResponseDTO> obtenerContratosDelCliente(Integer idCliente) {
        clienteRepository.findById(idCliente)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " + idCliente));

        List<Contrato> contratos = contratoRepository.findByClienteId(idCliente);
        return contratos.stream().map(this::mapContratoToResponse).toList();
    }

    // ─── Mapeos privados ───────────────────────────────────────────────────────

    private Cliente matToEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setNombres(dto.getNombres().toUpperCase());
        cliente.setApellidoPaterno(dto.getApellidoPaterno().toUpperCase());
        cliente.setApellidoMaterno(dto.getApellidoMaterno().toUpperCase());
        cliente.setCarnetIdentidad(dto.getCarnetIdentidad());
        cliente.setTelefonoUno(dto.getTelefonoUno());
        cliente.setTelefonoDos(dto.getTelefonoDos());
        cliente.setEstadoCliente(true);
        return cliente;
    }

    private ClienteResponseDTO matToResponseDTO(Cliente cliente) {
        ClienteResponseDTO dto = new ClienteResponseDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombres(cliente.getNombres());
        dto.setApellidoPaterno(cliente.getApellidoPaterno());
        dto.setApellidoMaterno(cliente.getApellidoMaterno());
        dto.setCarnetIdentidad(cliente.getCarnetIdentidad());
        dto.setTelefonoUno(cliente.getTelefonoUno());
        dto.setTelefonoDos(cliente.getTelefonoDos());
        dto.setFechaRegistro(cliente.getFechaRegistro());
        dto.setFechaActualizacion(cliente.getFechaActualizacion());
        dto.setEstado(cliente.getEstadoCliente());
        return dto;
    }

    private ClienteListarDTO matToListarDTO(Cliente cliente) {
        ClienteListarDTO dto = new ClienteListarDTO();
        dto.setIdCliente(cliente.getIdCliente());
        dto.setNombres(cliente.getNombres());
        dto.setApellidoPaterno(cliente.getApellidoPaterno());
        dto.setApellidoMaterno(cliente.getApellidoMaterno());
        dto.setTelefonoUno(cliente.getTelefonoUno());
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
}