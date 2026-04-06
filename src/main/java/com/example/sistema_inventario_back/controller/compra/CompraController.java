package com.example.sistema_inventario_back.controller.compra;

import com.example.sistema_inventario_back.dto.compra.*;
import com.example.sistema_inventario_back.service.compra.compra_interface.CompraService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/compra")
public class CompraController {

    private final CompraService compraService;

    // Controlador para listar a todas las compras
    @GetMapping("/listar")
    public ResponseEntity<CompraPageListarDTO> getAllComprasController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idCompra") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
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

        CompraPageListarDTO response = compraService.getAllComprasPaginationService(pageable);
        return ResponseEntity.ok(response);
    }

    // Crear nueva compra controller
    @PostMapping("/crear")
    public ResponseEntity<CompraResponseDTO> createCompra(
            @Valid @RequestBody CompraRequestDTO request,
            Principal principal) {

        String username = principal.getName();
        CompraResponseDTO response = compraService.createCompraService(request, username);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // Controlador para para mostrar las estadisticas
    @GetMapping("/estadisticas")
    public ResponseEntity<CompraEstadisticaDTO> getEstadisticasController(){
        return ResponseEntity.ok(compraService.getEstadisticasService());
    }

    // Controlador para ver los detalles de la compra
    @GetMapping("/detalle/{id}")
    public ResponseEntity<CompraDetalleDTO> obtenerDetalleCompraController(@PathVariable("id") Integer idCompra) {
        try {
            CompraDetalleDTO detalle = compraService.obtenerDetalleCompraService(idCompra);
            return ResponseEntity.ok(detalle);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
}