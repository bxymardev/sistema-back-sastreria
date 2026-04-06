package com.example.sistema_inventario_back.controller.tela;

import com.example.sistema_inventario_back.dto.tela.*;
import com.example.sistema_inventario_back.service.tela.tela_interface.TelaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/tela")
public class TelaController {

    @Autowired final TelaService telaService;

    // Controlador para crear un nueva tela
    @PostMapping(value = "/crearTela", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> crearTelaController(
            @RequestParam("color") String color,
            @RequestParam("codigoTela") String codigoTela,
            @RequestParam("idCategoriaTela") Integer idCategoriaTela,
            @RequestParam(value = "imagen") MultipartFile multipartFile
            ){
        try{
            TelaRequestDTO telaRequestDTO = new TelaRequestDTO();
            telaRequestDTO.setColor(color);
            telaRequestDTO.setCodigoTela(codigoTela);
            telaRequestDTO.setIdCategoriaTela(idCategoriaTela);

            TelaResponseDTO telaResponseDTO = telaService.crearTelaService(telaRequestDTO, multipartFile);
            return ResponseEntity.ok(telaResponseDTO);
        }catch (IllegalArgumentException e){
            return ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al crear la tela",
                    "mensaje", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Error interno del servidor",
                    "mensaje", e.getMessage()
            ));
        }
    }

    // Controlador para listar a todos las telas
    @GetMapping("/listarTelas")
    public ResponseEntity<TelaListarPageDTO> listarTelasController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idTela") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);
        TelaListarPageDTO responseTela = telaService.listarTelasService(pageable);

        return ResponseEntity.ok(responseTela);
    }

    // Controlador para buscar una tela mediante el id
    @GetMapping("/buscarTela/{idTela}")
    public ResponseEntity<TelaResponseDTO> buscarTelaIdController(
            @PathVariable Integer idTela
    ){
        TelaResponseDTO telaResponseDTO = telaService.buscarTelaById(idTela);
        return ResponseEntity.ok(telaResponseDTO);
    }

    // Controlador para filtrar por cualquier campo
    @GetMapping("/filtrarTela")
    public ResponseEntity<TelaListarPageDTO> filtrarTelaController(
            @RequestParam(required = false) String color,
            @RequestParam(required = false) String codigoTela,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idTela") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        TelaListarPageDTO telaListarPageDTO = telaService
                .buscarTelaFIltros(color, codigoTela, pageable);

        return ResponseEntity.ok(telaListarPageDTO);
    }

    // Controlador para listar telas por categoria
    @GetMapping("/categoria/listarTelas/{idCategoriaTela}")
    public ResponseEntity<TelaListarPageDTO> listarTelasPorCategoriaController(
            @PathVariable Integer idCategoriaTela,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return ResponseEntity.ok(telaService.listarTelasPorCategoria(idCategoriaTela, pageable));
    }

    // Controlador para actualizar la tela
    @PutMapping("/actualizarTela/{id}")
    public ResponseEntity<TelaResponseDTO> actualizarTelaController(
            @PathVariable Integer id,
            @Valid @RequestBody TelaUpdateDTO telaUpdateDTO
    ){
        telaUpdateDTO.setIdTela(id);
        TelaResponseDTO telaResponseDTO = telaService.actualizarTelaService(telaUpdateDTO);
        return ResponseEntity.ok(telaResponseDTO);
    }

    @GetMapping("/imagen/{idTela}")
    public ResponseEntity<TelaImagenDTO> obtenerImagenTelaController(
            @PathVariable Integer idTela
    ){
        TelaImagenDTO telaImagenDTO = telaService.obtenerImagenByTela(idTela);
        return ResponseEntity.ok(telaImagenDTO);
    }
}