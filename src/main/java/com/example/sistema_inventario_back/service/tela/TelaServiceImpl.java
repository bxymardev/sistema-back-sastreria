package com.example.sistema_inventario_back.service.tela;

import com.example.sistema_inventario_back.dto.tela.*;
import com.example.sistema_inventario_back.entity.tela.CategoriaTela;
import com.example.sistema_inventario_back.entity.tela.Tela;
import com.example.sistema_inventario_back.repository.tela.CategoriaTelaRepository;
import com.example.sistema_inventario_back.repository.tela.TelaRepository;
import com.example.sistema_inventario_back.service.tela.tela_interface.TelaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class TelaServiceImpl implements TelaService {

    @Autowired
    private TelaRepository telaRepository;

    @Autowired
    private CategoriaTelaRepository categoriaTelaRepository;

    // Servicio para crear un nuevo una nueva tela
    @Override
    public TelaResponseDTO crearTelaService(TelaRequestDTO telaRequestDTO, MultipartFile multipartFile) throws IOException {
        Tela tela = mapToEntity(telaRequestDTO, multipartFile);
        Tela telaGuardar = telaRepository.save(tela);

        return mapToResponseDTO(telaGuardar);
    }

    // Servicio para listar a todas la telas
    @Override
    public TelaListarPageDTO listarTelasService(Pageable pageable) {
        Page<TelaListarDTO> pageTelaListar = telaRepository.listarTelasRepository(pageable);

        TelaListarPageDTO telaListarPageDTO = new TelaListarPageDTO();
        telaListarPageDTO.setListaTelas(pageTelaListar.getContent());
        telaListarPageDTO.setPaginaActual(pageTelaListar.getNumber());
        telaListarPageDTO.setTotalElementos(pageTelaListar.getTotalElements());
        telaListarPageDTO.setTotalPaginas(pageTelaListar.getTotalPages());
        telaListarPageDTO.setTamanioPaginas(pageTelaListar.getSize());

        return telaListarPageDTO;
    }

    // Servicio para buscar una tela mediante el id
    @Override
    public TelaResponseDTO buscarTelaById(Integer idTela) {
        Tela tela = telaRepository.findById(idTela)
                .orElseThrow(() -> new RuntimeException("No se encontro la tela con id: "+idTela));

        return mapToResponseDTO(tela);
    }

    // Servicio para buscar una tela por los dos campos
    @Override
    public TelaListarPageDTO buscarTelaFIltros(String color, String codigoTela, Pageable pageable) {
        Specification<Tela> specification = Specification.where(null);

        if (color != null && !color.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("color")), "%" + color.toLowerCase() + "%")));
        }

        if (codigoTela != null && !codigoTela.isBlank()){
            specification = specification
                    .or(((root, query, criteriaBuilder) -> criteriaBuilder
                            .like(criteriaBuilder.lower(root.get("codigoTela")), "%" + codigoTela.toLowerCase() + "%")));
        }

        Page<Tela> telaPage = telaRepository.findAll(specification, pageable);

        List<TelaListarDTO> listaTelas = telaPage
                .getContent()
                .stream()
                .map(this::mapToListarDTO)
                .toList();

        TelaListarPageDTO telaListarPageDTO = new TelaListarPageDTO();
        telaListarPageDTO.setListaTelas(listaTelas);
        telaListarPageDTO.setTotalElementos(telaPage.getNumber());
        telaListarPageDTO.setTotalPaginas(telaPage.getTotalPages());
        telaListarPageDTO.setTotalElementos(telaPage.getTotalElements());
        telaListarPageDTO.setTamanioPaginas(telaPage.getSize());

        return telaListarPageDTO;
    }

    // Servicio para listar listar por categorias
    @Override
    public TelaListarPageDTO listarTelasPorCategoria(Integer idCategoria, Pageable pageable) {
        Page<Tela> telaPage = telaRepository.findByCategoria(idCategoria, pageable);

        List<TelaListarDTO> listaTelaDTO = telaPage
                .getContent()
                .stream()
                .map(this::mapToListarDTO)
                .toList();

        TelaListarPageDTO telaListarPageDTO = new TelaListarPageDTO();
        telaListarPageDTO.setListaTelas(listaTelaDTO);
        telaListarPageDTO.setPaginaActual(telaPage.getNumber());
        telaListarPageDTO.setTotalPaginas(telaPage.getTotalPages());
        telaListarPageDTO.setTotalElementos(telaPage.getTotalElements());
        telaListarPageDTO.setTamanioPaginas(telaPage.getSize());

        return telaListarPageDTO;
    }

    // Servicio para actualizar una tela
    @Override
    public TelaResponseDTO actualizarTelaService(TelaUpdateDTO telaUpdateDTO) {
        Tela tela = telaRepository.findById(telaUpdateDTO.getIdTela())
                .orElseThrow(() -> new RuntimeException("No se encontro la tela con id: " +telaUpdateDTO.getIdTela()));

        // Actualizar color de la tela
        if (telaUpdateDTO.getColor() != null && !telaUpdateDTO.getColor().isBlank()){
            tela.setColor(telaUpdateDTO.getColor());
        }

        // Actualizar codigo de la tela
        if (telaUpdateDTO.getCodigoTela() != null){
            tela.setCodigoTela(telaUpdateDTO.getCodigoTela());
        }

        // Agregar motivo de actualización
        if (telaUpdateDTO.getMotivoUpdate() != null){
            tela.setMotivoActualizacion(telaUpdateDTO.getMotivoUpdate());
        }

        // Actualizar categoria
        if (telaUpdateDTO.getIdCategoriaTela() != null){
            CategoriaTela categoriaTela = categoriaTelaRepository.findById(telaUpdateDTO.getIdCategoriaTela())
                    .orElseThrow(() -> new RuntimeException("No se encontro la categoria con id: " +telaUpdateDTO.getIdCategoriaTela()));
            tela.setCategoriaTela(categoriaTela);
        }

        Tela telaActualizado = telaRepository.save(tela);

        return mapToResponseDTO(telaActualizado);
    }

    // Servicio para ver la imagen de una tela
    @Override
    public TelaImagenDTO obtenerImagenByTela(Integer idTela) {

        Tela tela = telaRepository.findById(idTela)
                .orElseThrow(() -> new RuntimeException("No se encontró la tela con id: " +idTela));

        return null;
    }

    // Convertir DTO a Entidad
    private Tela mapToEntity(TelaRequestDTO telaRequestDTO, MultipartFile multipartFile){
        Tela tela = new Tela();

        tela.setColor(telaRequestDTO.getColor());
        tela.setCodigoTela(telaRequestDTO.getCodigoTela());

        //Categoria
        CategoriaTela categoriaTela = categoriaTelaRepository.findById(telaRequestDTO.getIdCategoriaTela())
                .orElseThrow(() -> new IllegalArgumentException("Categoria no encontrada"));
        tela.setCategoriaTela(categoriaTela);

        return tela;
    }

    // Convertir Entidad a DTO
    private TelaResponseDTO mapToResponseDTO(Tela tela){
        TelaResponseDTO telaResponseDTO = new TelaResponseDTO();
        telaResponseDTO.setIdTela(tela.getIdTela());
        telaResponseDTO.setColor(tela.getColor());
        telaResponseDTO.setCodigoTela(tela.getCodigoTela());
        telaResponseDTO.setFechaRegistro(tela.getFechaRegistro());
        telaResponseDTO.setIdCategoriaTela(tela.getCategoriaTela().getIdCategoriaTela());

        return telaResponseDTO;
    }

    // Convertir Entidad a DTO listar
    private TelaListarDTO mapToListarDTO(Tela tela){
        TelaListarDTO telaListarDTO = new TelaListarDTO();
        telaListarDTO.setIdTela(tela.getIdTela());
        telaListarDTO.setColor(tela.getColor());
        telaListarDTO.setCodigoTela(tela.getCodigoTela());
        telaListarDTO.setCodigoCategoriaTela(tela.getCategoriaTela().getCodigoCategoria());

        return telaListarDTO;
    }
}