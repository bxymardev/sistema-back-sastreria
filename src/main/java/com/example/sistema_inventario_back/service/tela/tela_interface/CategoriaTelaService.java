package com.example.sistema_inventario_back.service.tela.tela_interface;

import com.example.sistema_inventario_back.dto.tela.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CategoriaTelaService {

    // Servicio para listar a todas las categorias
    List<CategoriaTelaListarActivoDTO> listarCategoriasActivas();

    // Servicio para crear una nueva categoria
    CategoriaTelaResponseDTO crearCategoriaTelaService(CategoriaTelaRequestDTO categoriaTelaRequestDTO);

    // Servicio para listar a todas las categorias
    CategoriaTelaListarPageDTO listarCategoriaTelaService(Pageable pageable);

    // Servicio para buscar una categoria por id
    CategoriaTelaResponseDTO buscarCategoriaTelaById(Integer id);

    // Servicio para actualizar una categoria
    CategoriaTelaResponseDTO actualizarCategoriaTela(Integer id, CategoriaTelaRequestDTO categoriaTelaRequestDTO);

    // Servicio para actualizar solo el codigo de categoria tela
    CategoriaTelaResponseDTO actualizarCodigoCategoriaTela(Integer idCategoriaTela, String nuevoCodigoCategoriaTela);

    // Servicio para obtener las estadisticas de las categorias de tela
    CategoriaTelaEstadisticaDTO obtenerEstadisticasCategoriaService();
}