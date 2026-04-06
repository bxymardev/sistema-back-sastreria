package com.example.sistema_inventario_back.pdfexporter;

import com.example.sistema_inventario_back.dto.proveedor.proveedor.ProveedorResponseDTO;
import com.example.sistema_inventario_back.service.PdfExporter;
import com.lowagie.text.*;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;

import java.awt.*;
import java.io.ByteArrayOutputStream;

public class ProveedorPdfExporter implements PdfExporter<ProveedorResponseDTO> {
    @Override
    public byte[] exportToPDF(ProveedorResponseDTO proveedor){
        Document document = new Document(PageSize.LETTER);
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try{
            PdfWriter.getInstance(document, out);
            document.open();

            // Título
            Paragraph title = new Paragraph("DETALLES PROVEEDOR", FontFactory.getFont(FontFactory.TIMES_ROMAN, 16, Color.BLACK));
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" "));

            // Tabla con los detalles
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Definir Fuentes
            Font fontLabel = FontFactory.getFont(FontFactory.TIMES_BOLD, 12, Color.BLACK);
            Font fontValue = FontFactory.getFont(FontFactory.TIMES_ROMAN, 12, Color.DARK_GRAY);

            // Sub titulos
            addRow(table, "Nombre Comercial", proveedor.getNombreComercial(), fontLabel, fontValue);
            addRow(table, "Identificación Fiscal", proveedor.getIdentificacionFiscal(), fontLabel, fontValue);
            addRow(table, "Dirección", proveedor.getDireccion(), fontLabel, fontValue);

            document.add(table);
            document.close();

        }catch (DocumentException e){
            throw new RuntimeException("Error al generar PDF", e);
        }

        return out.toByteArray();
    }

    private void addRow(PdfPTable table, String label, String value, Font labelFont, Font valueFont){
        PdfPCell cell1 = new PdfPCell(new Phrase(label, labelFont));
        PdfPCell cell2 = new PdfPCell(new Phrase(value != null ? value : "Sin definir", valueFont));

        cell1.setBackgroundColor(Color.LIGHT_GRAY);
        cell1.setPadding(5);
        cell1.setPadding(5);
        table.addCell(cell1);
        table.addCell(cell2);
    }
}
