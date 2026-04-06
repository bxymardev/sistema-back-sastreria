package com.example.sistema_inventario_back.service.cliente;

import com.example.sistema_inventario_back.dto.cliente.ClienteListarDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteListarPageDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteRequestDTO;
import com.example.sistema_inventario_back.dto.cliente.ClienteResponseDTO;
import com.example.sistema_inventario_back.entity.cliente.Cliente;
import com.example.sistema_inventario_back.repository.cliente.ClienteRepository;
import com.example.sistema_inventario_back.service.cliente.cliente_interface.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServicelmpl implements ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Servicio para crear un cliente
    @Override
    public ClienteResponseDTO crearClienteService(ClienteRequestDTO clienteRequestDTO) {

        Cliente cliente = matToEntity(clienteRequestDTO);
        Cliente clienteGuardar = clienteRepository.save(cliente);

        return matToResponseDTO(clienteGuardar);
    }

    // Servicio para listar a todos los clientes
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
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado con id: " +id));

        return matToResponseDTO(cliente);
    }

    // Servicio para actualizar un cliente
    @Override
    public ClienteResponseDTO actualizarCliente(Integer id, ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        cliente.setNombre(clienteRequestDTO.getNombre().toUpperCase());
        cliente.setApellidoPaterno(clienteRequestDTO.getApellidoPaterno());
        cliente.setApellidoMaterno(clienteRequestDTO.getApellidoMaterno());
        cliente.setCarnetIdentidad(clienteRequestDTO.getCarnetIdentidad());
        cliente.setTelefono1(clienteRequestDTO.getTelefono1());
        cliente.setTelefono2(clienteRequestDTO.getTelefono2());

        Cliente clienteActualizado = clienteRepository.save(cliente);

        return matToResponseDTO(clienteActualizado);
    }

    // Servicio para buscar cliente por cualquier campo
    @Override
    public ClienteListarPageDTO buscarClienteFiltros(String nombre, String apellidoPaterno, String apellidoMaterno, String telefono1, Pageable pageable) {
        Specification<Cliente> specification = Specification.where(null);

        if(nombre != null && !nombre.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("nombre")), "%" + nombre.toLowerCase() + "%")));
        }

        if (apellidoPaterno != null && !apellidoPaterno.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("apellidoPaterno")), "%" + apellidoPaterno.toLowerCase() + "%")));
        }

        if (apellidoMaterno != null && !apellidoMaterno.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("apellidoMaterno")), "%" + apellidoMaterno.toLowerCase() + "%")));
        }

        if (telefono1 != null && !telefono1.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("telefono1")), "%" + telefono1.toLowerCase() + "%")));
        }

        Page<Cliente> clientePage = clienteRepository.findAll(specification, pageable);

        List<ClienteListarDTO> listaClientes = clientePage
                .getContent()
                .stream()
                .map(this::matToListarDTO)
                .toList();

        ClienteListarPageDTO clienteListarPageDTO = new ClienteListarPageDTO();
        clienteListarPageDTO.setClientes(listaClientes);
        clienteListarPageDTO.setTotalElementos(clientePage.getNumber());
        clienteListarPageDTO.setTotalPaginas(clientePage.getTotalPages());
        clienteListarPageDTO.setTotalElementos(clientePage.getTotalElements());
        clienteListarPageDTO.setTamanioPagina(clientePage.getSize());

        return clienteListarPageDTO;
    }

    private Cliente matToEntity(ClienteRequestDTO clienteRequestDTO){
        Cliente cliente = new Cliente();

        cliente.setNombre(clienteRequestDTO.getNombre());
        cliente.setApellidoPaterno(clienteRequestDTO.getApellidoPaterno());
        cliente.setApellidoMaterno(clienteRequestDTO.getApellidoMaterno());
        cliente.setCarnetIdentidad(clienteRequestDTO.getCarnetIdentidad());
        cliente.setTelefono1(clienteRequestDTO.getTelefono1());
        cliente.setTelefono2(clienteRequestDTO.getTelefono2());
        cliente.setEstadoCliente(true);

        return cliente;
    }

    private ClienteResponseDTO matToResponseDTO(Cliente cliente){

        ClienteResponseDTO clienteResponseDTO = new ClienteResponseDTO();

        clienteResponseDTO.setIdCliente(cliente.getIdCliente());
        clienteResponseDTO.setNombres(cliente.getNombre());
        clienteResponseDTO.setApellidoPaterno(cliente.getApellidoPaterno());
        clienteResponseDTO.setApellidoMaterno(cliente.getApellidoMaterno());
        clienteResponseDTO.setCarnetIdentidad(cliente.getCarnetIdentidad());
        clienteResponseDTO.setTelefono1(cliente.getTelefono1());
        clienteResponseDTO.setTelefono2(cliente.getTelefono2());
        clienteResponseDTO.setFechaRegistro(cliente.getFechaRegistro());
        clienteResponseDTO.setEstado(cliente.getEstadoCliente());

        return clienteResponseDTO;
    }

    private ClienteListarDTO matToListarDTO(Cliente cliente){
        ClienteListarDTO clienteListarDTO = new ClienteListarDTO();
        clienteListarDTO.setIdCliente(cliente.getIdCliente());
        clienteListarDTO.setNombre(cliente.getNombre());
        clienteListarDTO.setApellidoPaterno(cliente.getApellidoPaterno());
        clienteListarDTO.setApellidoMaterno(cliente.getApellidoMaterno());
        clienteListarDTO.setTelefono1(cliente.getTelefono1());

        return clienteListarDTO;
    }
}