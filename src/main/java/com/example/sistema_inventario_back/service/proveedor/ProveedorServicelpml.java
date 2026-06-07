package com.example.sistema_inventario_back.service.proveedor;

import com.example.sistema_inventario_back.dto.materia_prima.ProveedorActivoDTO;
import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import com.example.sistema_inventario_back.entity.proveedor.Proveedor;
import com.example.sistema_inventario_back.exception.proveedor.ProveedorNoEncontradoException;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.ProveedorService;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ProveedorServicelpml implements ProveedorService {

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Servicio para listar a todos los proveedores activos
    @Override
    public List<ProveedorActivoDTO> getProveedoresActivos() {
        return proveedorRepository.findAllByEstadoTrue();
    }

    // Servicio para crear un nuevo proveedor
    @Override
    public ProveedorResponseDTO createProveedor(ProveedorRequestDTO proveedorRequestDTO){
        Proveedor proveedor = matToEntity(proveedorRequestDTO);
        Proveedor saved = proveedorRepository.save(proveedor);

        return matToResponseDTO(saved);
    }

    // Servicio para listar a todos los Proveedores (nueva version)
    @Override
    public ProveedorPageListarDTO getAllProveedoresNew(Pageable pageable, Boolean estado){
        Page<ProveedorListarDTO> page = proveedorRepository.findAllResumen(estado, pageable);

        ProveedorPageListarDTO response = new ProveedorPageListarDTO();
        response.setProveedores(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    //Servicio para listar a todos los Proveedores
    @Override
    public ProveedorPageResponseDTO getAllProveedores(Pageable pageable){
        Page<Proveedor> page = proveedorRepository.findAll(pageable);

        List<ProveedorResponseDTO> dtoList = page.getContent()
                .stream()
                .map(this::matToResponseDTO)
                .toList();

        ProveedorPageResponseDTO responseDTO = new ProveedorPageResponseDTO();
        responseDTO.setProveedores(dtoList);
        responseDTO.setPaginaActual(page.getNumber());
        responseDTO.setTotalPaginas(page.getTotalPages());
        responseDTO.setTotalElementos(page.getTotalElements());
        responseDTO.setTamanioPagina(page.getSize());

        return responseDTO;
    }

    @Override
    public List<ProveedorListarDTO> getAllProveedoresSinPaginacion(Boolean estado) {
        return proveedorRepository.findAllResumenSinPaginacion(estado);
    }

    //Servicio para actualizar el proveedor
    @Override
    public ProveedorResponseDTO updateProveedor(Integer id, ProveedorUpdateDTO proveedorUpdateDTO){
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ProveedorNoEncontradoException(id));

        proveedor.setNombresEncargado(proveedorUpdateDTO.getNombresEncargado().toUpperCase());
        proveedor.setNombreComercial(proveedorUpdateDTO.getNombreComercial().toUpperCase());
        proveedor.setDireccion(proveedorUpdateDTO.getDireccion().toUpperCase());
        proveedor.setTipoProveedor(proveedorUpdateDTO.getTipoProveedor());
        proveedor.setNumeroUno(proveedorUpdateDTO.getTelefono1());
        proveedor.setFechaActualizacion(LocalDateTime.now());

        // Campos opcionales
        if (proveedorUpdateDTO.getIdentificacionFiscal() != null && !proveedorUpdateDTO.getIdentificacionFiscal().isBlank()){
            proveedor.setIdentificacionFiscal(proveedorUpdateDTO.getIdentificacionFiscal());
        }else{
            proveedor.setIdentificacionFiscal(null);
        }

        if (proveedorUpdateDTO.getTelefono2() != null && !proveedorUpdateDTO.getTelefono2().isBlank()){
            proveedor.setNumeroDos(proveedorUpdateDTO.getTelefono2());
        }else{
            proveedor.setNumeroDos(null);
        }

        Proveedor proveedorActualizado = proveedorRepository.save(proveedor);
        return matToResponseDTO(proveedorActualizado);
    }

    //Servicio para buscar un proveedor por id
    @Override
    public ProveedorResponseDTO getProveedorById(Integer id){

        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Proveedor no encontrado con id: " +id));

        return matToResponseDTO(proveedor);
    }

    // Servicio para mostrar los detalles del proveedor
    public ProveedorDetalleDTO getDetalleProveedor(Integer id) {
        Proveedor proveedor = proveedorRepository.findById(id)
            .orElseThrow(() -> new ProveedorNoEncontradoException(id));

        return mapToDetalleDTO(proveedor);
    }

    // Servicio para la filtracion
    @Override
    public ProveedorPageListarDTO buscarProveedorConFiltros(String searchTerm, Pageable pageable){

        Page<ProveedorListarDTO> page = proveedorRepository.findAllProveedoresConFiltros(
                searchTerm,
                pageable
        );

        ProveedorPageListarDTO response = new ProveedorPageListarDTO();
        response.setProveedores(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    @Override
    public ProveedorResponseDTO cambiarNombreProveedor(ProveedorCambiarNombreDTO nuevoNombre) {
        Proveedor proveedor = proveedorRepository.findById(nuevoNombre.getIdProveedor())
                .orElseThrow(() -> new ProveedorNoEncontradoException(nuevoNombre.getIdProveedor()));

        proveedor.setNombreComercial(nuevoNombre.getNuevoNombre().toUpperCase());
        proveedor.setFechaActualizacion(LocalDateTime.now());

        Proveedor proveedorActualizado = proveedorRepository.save(proveedor);

        return matToResponseDTO(proveedorActualizado);
    }

    @Override
    public ProveedorIndicadoresDTO obtenerIndicadoresService() {

        ProveedorIndicadoresDTO dto = new ProveedorIndicadoresDTO();
        dto.setTotal(proveedorRepository.contarTotal());
        dto.setActivos(proveedorRepository.contarActivos());
        dto.setInactivos(proveedorRepository.contarInactivos());

        LocalDateTime haceTresMesess = LocalDateTime.now().minusMonths(3);
        dto.setNuevosTresMeses(proveedorRepository.contarNuevosTresMeses(haceTresMesess));

        return dto;
    }

    private Proveedor matToEntity(ProveedorRequestDTO dto){
        Proveedor proveedor = new Proveedor();

        proveedor.setNombresEncargado(dto.getNombresEncargado().toUpperCase());
        proveedor.setNombreComercial(dto.getNombreComercial().toUpperCase());
        proveedor.setDireccion(dto.getDireccion().toUpperCase());
        proveedor.setNumeroUno(dto.getTelefono1());
        proveedor.setTipoProveedor(dto.getTipoProveedor());
        proveedor.setEstado(true);

        if (dto.getIdentificacionFiscal() != null){
            proveedor.setIdentificacionFiscal(dto.getIdentificacionFiscal().toUpperCase());
        }

        if (dto.getTelefono2() != null){
            proveedor.setNumeroDos(dto.getTelefono2());
        }

        return proveedor;
    }

    private ProveedorResponseDTO matToResponseDTO(Proveedor proveedor){
        ProveedorResponseDTO dto = new ProveedorResponseDTO();

        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreComercial(proveedor.getNombreComercial());
        dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTipoProveedor(proveedor.getTipoProveedor());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setFechaActualizacion(proveedor.getFechaActualizacion());
        return dto;
    }

    private ProveedorDetalleDTO mapToDetalleDTO(Proveedor proveedor){
        ProveedorDetalleDTO dto = new ProveedorDetalleDTO();

        dto.setIdProveedor(proveedor.getIdProveedor());
        dto.setNombreComercial(proveedor.getNombreComercial());
        dto.setNombresEncargado(proveedor.getNombresEncargado());
        dto.setIdentificacionFiscal(proveedor.getIdentificacionFiscal());
        dto.setNumeroUno(proveedor.getNumeroUno());
        dto.setNumeroDos(proveedor.getNumeroDos());
        dto.setDireccion(proveedor.getDireccion());
        dto.setTipoProveedor(proveedor.getTipoProveedor());
        dto.setEstado(proveedor.getEstado());
        dto.setMotivoEliminacion(proveedor.getMotivoEliminacion());
        dto.setFechaCreacion(proveedor.getFechaCreacion());
        dto.setFechaActualizacion(proveedor.getFechaActualizacion());

        return dto;
    }
}