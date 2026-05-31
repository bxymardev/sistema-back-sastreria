package com.example.sistema_inventario_back.service.tela.tela_interface;

import com.example.sistema_inventario_back.dto.tela.*;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface TelaService {

    // Servicio para registrar un nueva tela
    TelaResponseDTO crearTelaService(TelaRequestDTO telaRequestDTO, MultipartFile multipartFile) throws IOException;

    // Servicio para listar a todas las telas
    TelaListarPageDTO listarTelasService(Pageable pageable);

    // Servicio para buscar a una tela mediante el id de la tela
    TelaResponseDTO buscarTelaById(Integer idTela);

    // Servicio para buscar una tela por cualquier campo
    TelaListarPageDTO buscarTelaFIltros(
            String color,
            String codigoTela,
            Pageable pageable
    );

    // Servicio para listar telas por categoria
    TelaListarPageDTO listarTelasPorCategoria(Integer idCategoriaTela, Pageable pageable);

    // Servicio para actualizar la tela
    TelaResponseDTO actualizarTelaService(TelaUpdateDTO telaUpdateDTO);

    // Servicio para ver la imagen de una tela
    TelaImagenDTO obtenerImagenByTela(Integer idTela);
}