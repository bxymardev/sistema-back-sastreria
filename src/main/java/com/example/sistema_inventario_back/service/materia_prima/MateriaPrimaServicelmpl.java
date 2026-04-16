package com.example.sistema_inventario_back.service.materia_prima;

import com.example.sistema_inventario_back.dto.materia_prima.*;
import com.example.sistema_inventario_back.entity.compra.*;
import com.example.sistema_inventario_back.entity.tela.CategoriaTela;
import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import com.example.sistema_inventario_back.entity.tela.Tela;
import com.example.sistema_inventario_back.entity.usuario.Usuario;
import com.example.sistema_inventario_back.repository.compra.MateriaPrimaRepository;
import com.example.sistema_inventario_back.repository.proveedor.ProveedorRepository;
import com.example.sistema_inventario_back.repository.tela.CategoriaTelaRepository;
import com.example.sistema_inventario_back.repository.usuario.UserRepository;
import com.example.sistema_inventario_back.service.CloudinaryServiceImpl;
import com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface.MateriaPrimaService;
import com.example.sistema_inventario_back.service.tela.tela_interface.ImagenMateriaPrimaService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class MateriaPrimaServicelmpl implements MateriaPrimaService {

    @Autowired
    private MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Autowired
    private CategoriaTelaRepository categoriaTelaRepository;

    @Autowired
    private ImagenMateriaPrimaService imagenMateriaPrimaService;

    @Autowired
    private CloudinaryServiceImpl cloudinaryService;

    // Servicio para listar materia prima activos
    @Override
    public List<MateriaPrimaActivoDTO> getMateriaPrimaActivos() {
        return materiaPrimaRepository.findAllByEstadoActivo();
    }

    // Servicio para crear una nueva materia prima tipo tela
    @Override
    @Transactional
    public MateriaPrimaResponseDTO createMateriaPrimaTela(
            MateriaPrimaTelaRequestDTO dto,
            MultipartFile imagen,
            String username) {

        Usuario usuario = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        CategoriaTela categoria = categoriaTelaRepository.findById(dto.getIdCategoriaTela())
                .orElseThrow(() -> new EntityNotFoundException("Categoria de tela no encontrada"));

        // Crear Tela
        Tela tela = new Tela();
        tela.setColor(dto.getColor().toUpperCase());
        tela.setCodigoTela(dto.getCodigoTela());
        tela.setCategoriaTela(categoria);

        // Crear Materia Prima
        MateriaPrima mp = new MateriaPrima();
        mp.setNombreMateriaPrima(dto.getNombreMateriaPrima().toUpperCase());
        mp.setMarca(dto.getMarca());
        mp.setStockActual(dto.getStockActual());
        mp.setStockMinimo(dto.getStockMinimo());
        mp.setUnidadMedida(dto.getUnidadMedida());
        mp.setTipoMateriaPrima(TipoMateriaPrima.TELA);
        mp.setUbicacionAlmacen(dto.getUbicacionAlmacen());
        mp.setEstadoMateriaPrima(EstadoMateriaPrima.ACTIVO);
        mp.setUsuario(usuario);
        mp.setTela(tela);

        if (imagen != null && !imagen.isEmpty()){
            try{
                ImagenMateriaPrima imagenMp = imagenMateriaPrimaService.subirImagenService(imagen);
                mp.setImagenMateriaPrima(imagenMp);
            } catch (IOException e){
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
            }
        }

        mp = materiaPrimaRepository.save(mp);

        return matToResponseDTO(mp);
    }

    // Servicio para crear una nueva materia prima
    @Override
    @Transactional
    public MateriaPrimaResponseDTO createMateriaPrima(
            MateriaPrimaRequestDTO dto,
            MultipartFile imagen,
            String username) {

        Usuario usuario = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        MateriaPrima mp = new MateriaPrima();
        mp.setNombreMateriaPrima(dto.getNombreMateriaPrima().toUpperCase());
        mp.setMarca(dto.getMarca());
        mp.setModelo(dto.getModelo());
        mp.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0.0);
        mp.setStockMinimo(dto.getStockMinimo());
        mp.setUnidadMedida(dto.getUnidadMedida());
        mp.setTipoMateriaPrima(dto.getTipoMateriaPrima());
        mp.setUbicacionAlmacen(dto.getUbicacionAlmacen());
        mp.setEstadoMateriaPrima(EstadoMateriaPrima.ACTIVO);
        mp.setUsuario(usuario);

        // Subir imagen ANTES de guardar
        if (imagen != null && !imagen.isEmpty()) {
            try {
                ImagenMateriaPrima imagenMp = imagenMateriaPrimaService.subirImagenService(imagen);
                mp.setImagenMateriaPrima(imagenMp);
            } catch (IOException e) {
                throw new RuntimeException("Error al subir la imagen: " + e.getMessage());
            }
        }

        // Guardar materia prima
        mp = materiaPrimaRepository.save(mp);

        return matToResponseDTO(mp);
    }

    // Servicio para listar a toda la materia prima paginada (nueva version)
    @Override
    public MateriaPrimaPageListarDTO getAllMateriaPrima(Pageable pageable, TipoMateriaPrima tipo) {
        Page<MateriaPrimaListarDTO> page = materiaPrimaRepository.findAllMateriaPrimaByTipo(tipo, pageable);

        MateriaPrimaPageListarDTO response = new MateriaPrimaPageListarDTO();
        response.setMateriaPrima(page.getContent());
        response.setPaginaActual(page.getNumber());
        response.setTotalPaginas(page.getTotalPages());
        response.setTotalElementos(page.getTotalElements());
        response.setTamanioPagina(page.getSize());

        return response;
    }

    @Override
    public String getPreviewTelaUrl(Integer idCategoriaTela, String colorHex) {
        CategoriaTela categoriaTela = categoriaTelaRepository.findById(idCategoriaTela)
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada"));

        if (categoriaTela.getImagenBasePublicId() == null) {
            return null;
        }

        String hexLimpio = colorHex.replace("#", "");
        return cloudinaryService.generarUrlConColor(categoriaTela.getImagenBasePublicId(), hexLimpio);
    }

    // Convierte RequestDTO a entidad
    private MateriaPrima matToEntity(MateriaPrimaRequestDTO dto, String username){

        Usuario usuario = userRepository.findByNombreUsuario(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        MateriaPrima materiaPrima = new MateriaPrima();
        materiaPrima.setNombreMateriaPrima(dto.getNombreMateriaPrima());
        materiaPrima.setMarca(dto.getMarca());
        materiaPrima.setModelo(dto.getModelo());
        materiaPrima.setStockActual(dto.getStockActual() != null ? dto.getStockActual() : 0.0);
        materiaPrima.setStockMinimo(dto.getStockMinimo());
        materiaPrima.setUnidadMedida(dto.getUnidadMedida());
        materiaPrima.setTipoMateriaPrima(dto.getTipoMateriaPrima());
        materiaPrima.setUbicacionAlmacen(dto.getUbicacionAlmacen());
        materiaPrima.setEstadoMateriaPrima(EstadoMateriaPrima.ACTIVO);
        materiaPrima.setUsuario(usuario);

        return materiaPrima;
    }

    // Convierte entidad a ResponseDTO
    private MateriaPrimaResponseDTO matToResponseDTO(MateriaPrima materiaPrima){
        MateriaPrimaResponseDTO response = new MateriaPrimaResponseDTO();

        response.setIdMateriaPrima(materiaPrima.getIdMateriaPrima());
        response.setNombreMateriaPrima(materiaPrima.getNombreMateriaPrima());
        response.setMarca(materiaPrima.getMarca());
        response.setModelo(materiaPrima.getModelo());
        response.setStockActual(materiaPrima.getStockActual());
        response.setStockMinimo(materiaPrima.getStockMinimo());
        response.setUnidadMedida(materiaPrima.getUnidadMedida().name());
        response.setTipoMateriaPrima(materiaPrima.getTipoMateriaPrima().name());
        response.setUbicacionAlmacen(materiaPrima.getUbicacionAlmacen().name());
        response.setEstadoMateriaPrima(materiaPrima.getEstadoMateriaPrima().name());
        response.setPrecioUnitarioActual(materiaPrima.getPrecioUnitarioActual());
        response.setUsuario(materiaPrima.getUsuario().getNombreUsuario());

        return response;
    }
}