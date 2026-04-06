package com.example.sistema_inventario_back.service.tela.tela_interface;

import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImagenMateriaPrimaService {
    ImagenMateriaPrima subirImagenService(MultipartFile multipartFile) throws IOException;
    void eliminarImagenService(ImagenMateriaPrima imagenMateriaPrima) throws IOException;
}
