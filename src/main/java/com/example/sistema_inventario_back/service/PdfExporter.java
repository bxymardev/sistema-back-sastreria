package com.example.sistema_inventario_back.service;

public interface PdfExporter<T> {
    byte[] exportToPDF(T data);
}