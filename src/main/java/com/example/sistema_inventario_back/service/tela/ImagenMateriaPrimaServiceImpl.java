package com.example.sistema_inventario_back.service.tela;

import com.example.sistema_inventario_back.entity.tela.ImagenMateriaPrima;
import com.example.sistema_inventario_back.repository.tela.ImagenMateriaPrimaRepository;
import com.example.sistema_inventario_back.service.CloudinaryService;
import com.example.sistema_inventario_back.service.tela.tela_interface.ImagenMateriaPrimaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class ImagenMateriaPrimaServiceImpl implements ImagenMateriaPrimaService {

    @Autowired
    private ImagenMateriaPrimaRepository imagenMateriaPrimaRepository;

    @Autowired
    private CloudinaryService cloudinaryService;

    @Override
    public ImagenMateriaPrima subirImagenService(MultipartFile multipartFile) throws IOException {
        Map<?, ?> subirResultado = cloudinaryService.subir(multipartFile);
        String imagenUrl = (String) subirResultado.get("url");
        String imagenId = (String) subirResultado.get("public_id");
        ImagenMateriaPrima imagenMateriaPrima = new ImagenMateriaPrima(
                multipartFile.getOriginalFilename(),
                imagenUrl,
                imagenId
        );

        return imagenMateriaPrimaRepository.save(imagenMateriaPrima);
    }

    @Override
    public void eliminarImagenService(ImagenMateriaPrima imagenMateriaPrima) throws IOException {
        cloudinaryService.eliminar(imagenMateriaPrima.getImagenId());
        imagenMateriaPrimaRepository.deleteById(imagenMateriaPrima.getIdImagenMateriaPrima());
    }
}