package com.example.sistema_inventario_back.config;

import com.example.sistema_inventario_back.pdfexporter.ProveedorPdfExporter;
import com.example.sistema_inventario_back.service.PdfExporter;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PdfExporterFactory {

    private final Map<String, PdfExporter<?>> exporters = new HashMap<>();

    public PdfExporterFactory(){
        exporters.put("proveedor", new ProveedorPdfExporter());
    }

    @SuppressWarnings("uncheched")
    public <T> PdfExporter<T> getExporter(String type){
        return (PdfExporter<T>) exporters.get(type);
    }
}