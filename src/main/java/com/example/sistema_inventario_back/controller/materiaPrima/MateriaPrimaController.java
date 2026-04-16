package com.example.sistema_inventario_back.controller.materiaPrima;

import com.example.sistema_inventario_back.dto.materia_prima.*;
import com.example.sistema_inventario_back.entity.compra.TipoMateriaPrima;
import com.example.sistema_inventario_back.entity.compra.UbicacionAlmacen;
import com.example.sistema_inventario_back.entity.proveedor.UnidadMedida;
import com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface.MateriaPrimaService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/materiaPrima")
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;

    // Controlador para listar a materias prima activos
    @GetMapping("/activos")
    public ResponseEntity<List<MateriaPrimaActivoDTO>> getMateriaPrimasActivas() {
        return ResponseEntity.ok(materiaPrimaService.getMateriaPrimaActivos());
    }

    // Controlador para crear una materia prima tipo tela
    @PostMapping(value = "/crear", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> createMateriaPrimaTela(

            // Campos comunes de MateriaPrima
            @RequestParam("nombreMateriaPrima") String nombreMateriaPrima,
            @RequestParam(value = "marca", required = false) String marca,
            @RequestParam(value = "modelo", required = false) String modelo,
            @RequestParam("stockActual") Double stockActual,
            @RequestParam("stockMinimo") Double stockMinimo,
            @RequestParam("unidadMedida") UnidadMedida unidadMedida,
            @RequestParam("tipoMateriaPrima") TipoMateriaPrima tipoMateriaPrima,
            @RequestParam("ubicacionAlmacen") UbicacionAlmacen ubicacionAlmacen,

            // Campos de Tela (solo si tipoMateriaPrima = TELA)
            @RequestParam(value = "color", required = false) String color,
            @RequestParam(value = "codigoTela", required = false) String codigoTela,
            @RequestParam(value = "idCategoriaTela", required = false) Integer idCategoriaTela,
            // Imagen (opcional para cualquier tipo)
            @RequestParam(value = "imagen", required = false) MultipartFile imagen,
            Principal principal) {

        try{
            String username = principal.getName();
            MateriaPrimaResponseDTO response;

            if (TipoMateriaPrima.TELA.equals(tipoMateriaPrima)) {
                if (color == null || idCategoriaTela == null){
                    return ResponseEntity.badRequest().body(Map.of(
                            "error", "Campos requeridos",
                            "mensaje", "Para tipo TELA, el color y la categoría son obligatorios"
                    ));
                }

                MateriaPrimaTelaRequestDTO dto = new MateriaPrimaTelaRequestDTO();
                dto.setNombreMateriaPrima(nombreMateriaPrima);
                dto.setMarca(marca);
                dto.setStockActual(stockActual);
                dto.setStockMinimo(stockMinimo);
                dto.setUnidadMedida(unidadMedida);
                dto.setUbicacionAlmacen(ubicacionAlmacen);
                dto.setColor(color);
                dto.setCodigoTela(codigoTela);
                dto.setIdCategoriaTela(idCategoriaTela);

                response = materiaPrimaService.createMateriaPrimaTela(dto, imagen, username);
            } else{
                MateriaPrimaRequestDTO dto = new MateriaPrimaRequestDTO();
                dto.setNombreMateriaPrima(nombreMateriaPrima);
                dto.setMarca(marca);
                dto.setModelo(modelo);
                dto.setStockActual(stockActual);
                dto.setStockMinimo(stockMinimo);
                dto.setUnidadMedida(unidadMedida);
                dto.setTipoMateriaPrima(tipoMateriaPrima);
                dto.setUbicacionAlmacen(ubicacionAlmacen);

                response = materiaPrimaService.createMateriaPrima(dto, imagen, username);
            }

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (IllegalArgumentException e){
            return  ResponseEntity.badRequest().body(Map.of(
                    "error", "Error al crear la materia prima",
                    "mensaje", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "error", "Error interno del servidor",
                    "mensaje", e.getMessage()
            ));
        }
    }

    // Controlador para listar materias primas con paginacion
    @GetMapping("/listarMateriaPrima")
    public ResponseEntity<MateriaPrimaPageListarDTO> listMateriaPrimaController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idMateriaPrima") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) TipoMateriaPrima tipo
    ){

        // Validacion de parametros
        int paginaActual = Math.max(page, 0);
        int tamanioPagina = Math.min(Math.max(size, 1), 100);

        // Construccion de ordenamiento
        Sort sort = sortDir.equalsIgnoreCase("asc") ?
                Sort.by(sortBy).ascending() :
                Sort.by(sortBy).descending();

        // Paginacion con validacion incluida
        Pageable pageable = PageRequest.of(paginaActual, tamanioPagina, sort);

        MateriaPrimaPageListarDTO response = materiaPrimaService.getAllMateriaPrima(pageable, tipo);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/previewTela")
    public ResponseEntity<Map<String, String>> getPreviewTela(
            @RequestParam Integer idCategoriaTela,
            @RequestParam String color) {
        String url = materiaPrimaService.getPreviewTelaUrl(idCategoriaTela, color);
        if (url == null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(Map.of("previewUrl", url));
    }
}