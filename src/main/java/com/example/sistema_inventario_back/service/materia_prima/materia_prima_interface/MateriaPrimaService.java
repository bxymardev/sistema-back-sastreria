package com.example.sistema_inventario_back.service.materia_prima.materia_prima_interface;

import com.example.sistema_inventario_back.dto.materia_prima.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface MateriaPrimaService {

    // Servicio para crear una materia prima
    MateriaPrimaResponseDTO createMateriaPrima(MateriaPrimaRequestDTO dto, MultipartFile imagen, String username);

    // Servicio para listar todas las materias primas de manera paginada
    MateriaPrimaPageListarDTO getAllMateriaPrima(Pageable pageable);

    // Servicio para listar a materias primas activas
    List<MateriaPrimaActivoDTO> getMateriaPrimaActivos();

    // Servicio para crear una materia prima tipo tela
    MateriaPrimaResponseDTO createMateriaPrimaTela(MateriaPrimaTelaRequestDTO dto, MultipartFile imagen, String username);

    // Servicio para ver un preview del color de la tela
    String getPreviewTelaUrl(Integer idCategoriaTela, String colorHex);
}