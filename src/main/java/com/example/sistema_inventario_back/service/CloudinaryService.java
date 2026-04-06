package com.example.sistema_inventario_back.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Map;

public interface CloudinaryService {
    Map<?, ?> subir(MultipartFile multipartFile) throws IOException;

    Map<?, ?> subir(File file) throws IOException;

    Map<?, ?> eliminar(String id) throws IOException;

    String generarUrlConColor(String publicID, String colorHex);
}
