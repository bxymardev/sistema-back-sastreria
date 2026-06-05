package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.materia_prima.ProveedorActivoDTO;
import com.example.sistema_inventario_back.dto.proveedor.proveedor.*;
import com.example.sistema_inventario_back.pdfexporter.ProveedorPdfExporter;
import com.example.sistema_inventario_back.service.proveedor.proveedor_interface.ProveedorService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/proveedor")
public class ProveedorController {

    @Autowired
    private final ProveedorService proveedorService;

    // Controlador para listar a todos los proveedores activos
    @GetMapping("/activos")
    public ResponseEntity<List<ProveedorActivoDTO>> getProveedoresActivos() {
        List<ProveedorActivoDTO> proveedores = proveedorService.getProveedoresActivos();
        return ResponseEntity.ok(proveedores);
    }

    // Controlador para buscar un Proveedor
    @GetMapping("/buscar_proveedor")
    public ResponseEntity<ProveedorPageListarDTO> filtrarProveedorController(
            @RequestParam(required = false) String search,
            @RequestParam(defaultValue = "0") int pagina,
            @RequestParam(defaultValue = "5") int tamanio
    ) {
        Pageable pageable = PageRequest.of(pagina, tamanio);
        ProveedorPageListarDTO result = proveedorService.buscarProveedorConFiltros(search, pageable);

        return ResponseEntity.ok(result);
    }

    //Controlador para crear un nuevo Proveedor
    @PostMapping("/crear_proveedor")
    public ResponseEntity<ProveedorResponseDTO> createProveedorController(
            @Valid @RequestBody ProveedorRequestDTO proveedorRequestDTO
            ){
        ProveedorResponseDTO responseDTO = proveedorService.createProveedor(proveedorRequestDTO);
        return ResponseEntity.ok(responseDTO);
    }

    // Controlador para listar a todos los Proveedores (nueva version)
    @GetMapping("/listar_proveedores/nueva")
    public ResponseEntity<ProveedorPageListarDTO> getAllProveedoresNewVersionController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "idProveedor") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir,
            @RequestParam(required = false) Boolean estado
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

        ProveedorPageListarDTO response = proveedorService.getAllProveedoresNew(pageable, estado);
        return ResponseEntity.ok(response);
    }

    //Controlador para listar a todos los Proveedores (antigua version)
    @GetMapping("/listar_proveedores")
    public ProveedorPageResponseDTO getAllProveedoresController(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "idProveedor") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ){
        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        return proveedorService.getAllProveedores(pageable);
    }

    // Controlador paara listar a todos los proveedores sin paginación
    @GetMapping("/listar_proveedores/todos")
    public ResponseEntity<List<ProveedorListarDTO>> getAllProveedoresSinPaginacionController(
        @RequestParam(required = false) Boolean estado
    ) {
        List<ProveedorListarDTO> response = proveedorService.getAllProveedoresSinPaginacion(estado);
        return ResponseEntity.ok(response);
    }
    

    //Controlador para buscar un proveedor por id
    @GetMapping("/buscar_proveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> getProveedorByIdController(
            @PathVariable Integer id
    ){
        ProveedorResponseDTO responseDTO = proveedorService.getProveedorById(id);
        return ResponseEntity.ok(responseDTO);
    }

    //Controlador para actualizar el proveedor
    @PutMapping("/actualizar_proveedor/{id}")
    public ResponseEntity<ProveedorResponseDTO> getUpdateProveedorController(
            @PathVariable Integer id,
            @Valid @RequestBody ProveedorUpdateDTO proveedorUpdateDTO
    ){
        ProveedorResponseDTO proveedor = proveedorService.updateProveedor(id, proveedorUpdateDTO);
        return ResponseEntity.ok(proveedor);
    }

    //Controlador para exportar pdf
    @GetMapping("/exportar/pdf/{id}")
    public void exportarProveedorPorId(
            @PathVariable Integer id,
            HttpServletResponse response
    ) throws IOException {
        ProveedorResponseDTO proveedor = proveedorService.getProveedorById(id);
        ProveedorPdfExporter exporter = new ProveedorPdfExporter();

        byte[] pdfBytes = exporter.exportToPDF(proveedor);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=proveedor_" + id + ".pdf");
        response.getOutputStream().write(pdfBytes);
        response.getOutputStream().flush();
    }

    @PutMapping("/cambiar_nombre")
    public ResponseEntity<ProveedorResponseDTO> cambiarNombreProveedorController(@Valid @RequestBody ProveedorCambiarNombreDTO nuevoNombre){
        ProveedorResponseDTO proveedorActualizado = proveedorService.cambiarNombreProveedor(nuevoNombre);
        return ResponseEntity.ok(proveedorActualizado);
    }

    @GetMapping("/indicadores")
    public ResponseEntity<ProveedorIndicadoresDTO> obtenerIndicadoresController() {
        return ResponseEntity.ok(proveedorService.obtenerIndicadoresService());
    }
}