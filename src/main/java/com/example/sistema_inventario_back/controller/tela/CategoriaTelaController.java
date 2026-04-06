package com.example.sistema_inventario_back.controller.tela;

import com.example.sistema_inventario_back.dto.tela.*;
import com.example.sistema_inventario_back.service.tela.tela_interface.CategoriaTelaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/categoriaTela")
public class CategoriaTelaController {

    @Autowired final CategoriaTelaService categoriaTelaService;

    // Controlador para obtener estadisticas de las categorias
    @GetMapping("/estadisticas")
    public ResponseEntity<CategoriaTelaEstadisticaDTO> obtenerEstadisticasController(){
        CategoriaTelaEstadisticaDTO estadisticaDTO = categoriaTelaService.obtenerEstadisticasCategoriaService();
        return ResponseEntity.ok(estadisticaDTO);
    }

    // Controlador para listar a todas las categorias activas
    @GetMapping("/activos")
    public ResponseEntity<List<CategoriaTelaListarActivoDTO>> listarCategoriasActivasController(){
        List<CategoriaTelaListarActivoDTO> categorias = categoriaTelaService.listarCategoriasActivas();
        return ResponseEntity.ok(categorias);
    }

    // Controlador para crear una nueva Categoria de una nueva tela
    @PostMapping("/crearCategoriaTela")
    public ResponseEntity<CategoriaTelaResponseDTO> crearCategoriaTelaController(
            @Valid @RequestBody CategoriaTelaRequestDTO categoriaTelaRequestDTO
            ){
        CategoriaTelaResponseDTO categoriaTelaResponseDTO = categoriaTelaService.crearCategoriaTelaService(categoriaTelaRequestDTO);
        return ResponseEntity.ok(categoriaTelaResponseDTO);
    }

    // Controlador para listar a todos las categorias
    @GetMapping("/listarCategorias")
    public ResponseEntity<CategoriaTelaListarPageDTO> listarCategoriasController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCategoriaTela") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);
        CategoriaTelaListarPageDTO responseCategoriaTela = categoriaTelaService.listarCategoriaTelaService(pageable);

        return ResponseEntity.ok(responseCategoriaTela);
    }

    // Controlador para buscar una categoria mediante el id
    @GetMapping("/buscarCategoriaTela/{id}")
    public ResponseEntity<CategoriaTelaResponseDTO> buscarCategoriaTelaIdController(
            @PathVariable Integer id
    ){
        CategoriaTelaResponseDTO categoriaTelaResponseDTO = categoriaTelaService.buscarCategoriaTelaById(id);
        return ResponseEntity.ok(categoriaTelaResponseDTO);
    }

    // Controlador para actualizar una categoria
    @PutMapping("/actualizarCategoria/{id}")
    public ResponseEntity<CategoriaTelaResponseDTO> actualizarCategoriaTelaController(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaTelaRequestDTO categoriaTelaRequestDTO
    ){
        CategoriaTelaResponseDTO categoriaTelaResponseDTO = categoriaTelaService.actualizarCategoriaTela(id, categoriaTelaRequestDTO);
        return ResponseEntity.ok(categoriaTelaResponseDTO);
    }

    // Controlador para actualizar solo el codigo de la categoria de la tela
    @PutMapping("/actualizarCodigoCategoria/{id}")
    public ResponseEntity<CategoriaTelaResponseDTO> actualizarCodigoCategoriaTela(
            @PathVariable Integer id,
            @Valid @RequestBody CategoriaTelaActualizarCodigoDTO categoriaTelaActualizarCodigoDTO
    ){
        CategoriaTelaResponseDTO categoriaTelaResponseDTO = categoriaTelaService
                .actualizarCodigoCategoriaTela(id, categoriaTelaActualizarCodigoDTO.codigoCategoriaTela());

        return ResponseEntity.ok(categoriaTelaResponseDTO);
    }
}