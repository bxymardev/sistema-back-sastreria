package com.example.sistema_inventario_back.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
public class CloudinaryServiceImpl implements CloudinaryService{

    private Cloudinary cloudinary;

    public CloudinaryServiceImpl() {
        Map<String, String> valoresMap = new HashMap<>();
        valoresMap.put("cloud_name", "dx3cnfpgz");
        valoresMap.put("api_key", "431225174716486");
        valoresMap.put("api_secret", "aer4ahBws089SxeCk9YPllqVuhM");
        cloudinary = new Cloudinary(valoresMap);
    }

    @Override
    public Map<?, ?> subir(File file) throws IOException {
        Map<String, String> resultado = cloudinary.uploader().upload(file, ObjectUtils.emptyMap());
        Files.deleteIfExists(file.toPath());

        String url = (String) resultado.get("secure_url");
        String publicId = (String) resultado.get("public_id");

        Map<String, Object> response = new HashMap<>();
        response.put("url", url);
        response.put("public_id", publicId);

        return response;
    }

    @Override
    public Map<?, ?> subir(MultipartFile multipartFile) throws IOException {
        File fileObtenido = convertir(multipartFile);
        Map<String, String> resultado = cloudinary.uploader().upload(fileObtenido, ObjectUtils.emptyMap());
        Files.deleteIfExists(fileObtenido.toPath());

        String url = (String) resultado.get("secure_url");
        String publicId = (String) resultado.get("public_id");

        Map<String, Object> response = new HashMap<>();
        response.put("url", url);
        response.put("public_id", publicId);

        return response;
    }

    @Override
    public Map<?, ?> eliminar(String id) throws IOException {
        Map<?, ?> result = cloudinary.uploader().destroy(id, ObjectUtils.emptyMap());
        return result;
    }

    private File convertir(MultipartFile multipartFile) throws IOException{
        File file = new File(Objects.requireNonNull(multipartFile.getOriginalFilename()));

        try(FileOutputStream flujoSalida = new FileOutputStream(file)){
            flujoSalida.write(multipartFile.getBytes());
        }

        return file;
    }

    @Override
    public String generarUrlConColor(String publicID, String colorHex) {
        return cloudinary.url()
                .transformation(new Transformation()
                        .effect("colorize:80")
                        .color("#" +colorHex)
                )
                .generate(publicID);
    }
}