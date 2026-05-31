package com.example.sistema_inventario_back.controller.proveedor;

import com.example.sistema_inventario_back.dto.materia_prima.MateriaPrimaActivoDTO;
import com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface.MateriaPrimaService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@AllArgsConstructor
@RequestMapping("/api/proveedor")
public class ProveedorMateriaPrimaController {

    @Autowired
    private final MateriaPrimaService materiaPrimaService;

    // Controlador para listar las materias primas compradas a un proveedor
    @GetMapping("/{idProveedor}/materias-primas")
    public ResponseEntity<List<MateriaPrimaActivoDTO>> getMateriasPrimasByProveedor(@PathVariable Integer idProveedor) {
        List<MateriaPrimaActivoDTO> materiasPrimas = materiaPrimaService.getMateriaPrimaByProveedorId(idProveedor);
        return ResponseEntity.ok(materiasPrimas);
    }
}