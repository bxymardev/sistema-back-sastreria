package com.example.sistema_inventario_back.service.tela;

import com.example.sistema_inventario_back.dto.tela.*;
import com.example.sistema_inventario_back.entity.tela.CategoriaTela;
import com.example.sistema_inventario_back.repository.tela.CategoriaTelaRepository;
import com.example.sistema_inventario_back.service.tela.tela_interface.CategoriaTelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaTelaServicelmpl implements CategoriaTelaService {

    @Autowired
    private CategoriaTelaRepository categoriaTelaRepository;

    // Servicio para obtener las estadisticas de las categorias de la tela
    @Override
    public CategoriaTelaEstadisticaDTO obtenerEstadisticasCategoriaService() {

        Integer totalCategorias = categoriaTelaRepository.countTotalCategorias();
        String categoriaMasRotacion = categoriaTelaRepository.findCategoriaMasRotacion()
                .orElse("-");
        String ultimaCategoriaActualizada = categoriaTelaRepository.findUltimaCategoriaActualizada()
                .orElse("-");
        String categoriaObsoleta = categoriaTelaRepository.findCategoriaObsoleta()
                .orElse("-");

        return new CategoriaTelaEstadisticaDTO(
                totalCategorias,
                categoriaMasRotacion,
                ultimaCategoriaActualizada,
                categoriaObsoleta
        );
    }

    // Servicio para listar a todas las categorias activas
    @Override
    public List<CategoriaTelaListarActivoDTO> listarCategoriasActivas() {
        return categoriaTelaRepository.findAllCategoriasActivas();
    }

    // Servicio para crear una nueva categoria
    @Override
    public CategoriaTelaResponseDTO crearCategoriaTelaService(CategoriaTelaRequestDTO categoriaTelaRequestDTO) {
        CategoriaTela categoriaTela = mapToEntity(categoriaTelaRequestDTO);
        CategoriaTela categoriaTelaGuardar = categoriaTelaRepository.save(categoriaTela);

        return mapToResponseDTO(categoriaTelaGuardar);
    }

    // Servicio para listar a todas las categorias
    @Override
    public CategoriaTelaListarPageDTO listarCategoriaTelaService(Pageable pageable) {
        Page<CategoriaTelaListarDTO> pageCategoriaListar = categoriaTelaRepository.listarCategoriasRepository(pageable);

        CategoriaTelaListarPageDTO categoriaTelaListarPageDTO = new CategoriaTelaListarPageDTO();
        categoriaTelaListarPageDTO.setListaCategorias(pageCategoriaListar.getContent());
        categoriaTelaListarPageDTO.setPaginaActual(pageCategoriaListar.getNumber());
        categoriaTelaListarPageDTO.setTotalElementos(pageCategoriaListar.getTotalElements());
        categoriaTelaListarPageDTO.setTotalPaginas(pageCategoriaListar.getTotalPages());
        categoriaTelaListarPageDTO.setTamanioPagina(pageCategoriaListar.getSize());

        return categoriaTelaListarPageDTO;
    }

    // Servicio para actualizar una categoria
    @Override
    public CategoriaTelaResponseDTO actualizarCategoriaTela(Integer id, CategoriaTelaRequestDTO categoriaTelaRequestDTO) {
        CategoriaTela categoriaTela = categoriaTelaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria no encontrado"));

        categoriaTela.setCodigoCategoria(categoriaTelaRequestDTO.getCodigoCategoria());
        categoriaTela.setComposicion(categoriaTelaRequestDTO.getComposicion());
        categoriaTela.setTitulo(categoriaTelaRequestDTO.getTitulo());
        categoriaTela.setPeso(categoriaTelaRequestDTO.getPeso());
        categoriaTela.setAncho(categoriaTelaRequestDTO.getAncho());
        categoriaTela.setDensidad(categoriaTelaRequestDTO.getDensidad());
        categoriaTela.setLigamento(categoriaTelaRequestDTO.getLigamento());
        categoriaTela.setAcabado(categoriaTelaRequestDTO.getAcabado());

        CategoriaTela categoriaTelaActualizado = categoriaTelaRepository.save(categoriaTela);

        return mapToResponseDTO(categoriaTelaActualizado);
    }

    // Servicio para buscar una categoria por id
    @Override
    public CategoriaTelaResponseDTO buscarCategoriaTelaById(Integer id) {
        CategoriaTela categoriaTela = categoriaTelaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Categoria Tela no encontrado con id: " +id));

        return mapToResponseDTO(categoriaTela);
    }

    // Servicio para actualizar solo el codigo de la categoria tela
    @Override
    public CategoriaTelaResponseDTO actualizarCodigoCategoriaTela(Integer idCategoriaTela, String nuevoCodigoCategoriaTela) {

        CategoriaTela categoriaTela = categoriaTelaRepository.findById(idCategoriaTela)
                .orElseThrow(() -> new RuntimeException("Categoria Tela no encontrada con id: " +idCategoriaTela));
        boolean codigoCategoriaExiste =  categoriaTelaRepository
                .verificarCodigoCategoriaTelaById(nuevoCodigoCategoriaTela, idCategoriaTela);

        if (codigoCategoriaExiste){
            throw new RuntimeException("El codigo de categoria: " +nuevoCodigoCategoriaTela+ " ya esta registrado");
        }

        categoriaTela.setCodigoCategoria(nuevoCodigoCategoriaTela);
        CategoriaTela categoriaTelaActualizado = categoriaTelaRepository.save(categoriaTela);

        return mapToResponseDTO(categoriaTelaActualizado);
    }

    // Convertir de DTO a Entidad
    private CategoriaTela mapToEntity(CategoriaTelaRequestDTO categoriaTelaRequestDTO){
        CategoriaTela categoriaTela = new CategoriaTela();

        categoriaTela.setCodigoCategoria(categoriaTelaRequestDTO.getCodigoCategoria());
        categoriaTela.setComposicion(categoriaTelaRequestDTO.getComposicion());
        categoriaTela.setTitulo(categoriaTelaRequestDTO.getTitulo());
        categoriaTela.setPeso(categoriaTelaRequestDTO.getPeso());
        categoriaTela.setAncho(categoriaTelaRequestDTO.getAncho());
        categoriaTela.setDensidad(categoriaTelaRequestDTO.getDensidad());
        categoriaTela.setLigamento(categoriaTelaRequestDTO.getLigamento());
        categoriaTela.setAcabado(categoriaTelaRequestDTO.getAcabado());

        return categoriaTela;
    }

    // Convertir de Entidad a DTO
    private CategoriaTelaResponseDTO mapToResponseDTO(CategoriaTela categoriaTela){
        CategoriaTelaResponseDTO categoriaTelaResponseDTO = new CategoriaTelaResponseDTO();

        categoriaTelaResponseDTO.setIdCategoriaTela(categoriaTela.getIdCategoriaTela());
        categoriaTelaResponseDTO.setCodigoCategoria(categoriaTela.getCodigoCategoria());
        categoriaTelaResponseDTO.setTitulo(categoriaTela.getTitulo());
        categoriaTelaResponseDTO.setComposicion(categoriaTela.getComposicion());
        categoriaTelaResponseDTO.setPeso(categoriaTela.getPeso());
        categoriaTelaResponseDTO.setAncho(categoriaTela.getAncho());
        categoriaTelaResponseDTO.setDensidad(categoriaTela.getDensidad());
        categoriaTelaResponseDTO.setLigamento(categoriaTela.getLigamento());
        categoriaTelaResponseDTO.setAcabado(categoriaTela.getAcabado());
        categoriaTelaResponseDTO.setFechaRegistro(categoriaTela.getFechaRegistro());

        return categoriaTelaResponseDTO;
    }
}