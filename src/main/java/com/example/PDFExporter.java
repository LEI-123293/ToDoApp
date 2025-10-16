package com.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List;

public class PDFExporter extends VerticalLayout {

    private final List<String> tasks;

    public PDFExporter(List<String> tasks) {
        this.tasks = tasks;
        Button exportButton = new Button("Exportar para PDF", e -> exportToPdf());
        add(exportButton);
    }

    private void exportToPdf() {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {

                // Título
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Lista de Tarefas - ToDoApp");
                contentStream.endText();

                // Tarefas
                float yPosition = 700;
                for (String task : tasks) {
                    contentStream.beginText();
                    contentStream.setFont(PDType1Font.HELVETICA, 12);
                    contentStream.newLineAtOffset(50, yPosition);
                    contentStream.showText(task);
                    contentStream.endText();
                    yPosition -= 20;
                }
            }

            document.save("tarefas.pdf");
            System.out.println("✅ PDF gerado: tarefas.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
