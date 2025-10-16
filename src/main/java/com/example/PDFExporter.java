package com.example;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

import java.io.IOException;
import java.util.List; // Assuma uma lista de tarefas simples para exemplo

public class PDFExporter extends VerticalLayout {
    public PDFExporter() {
        Button exportButton = new Button("Exportar para PDF", e -> exportToPdf());

        add(exportButton);
    }

    private void exportToPdf() {
        try {
            PDDocument document = new PDDocument();
            PDPage page = new PDPage(PDRectangle.A4);
            document.addPage(page);

            PDPageContentStream contentStream = new PDPageContentStream(document, page);

            // Título
            contentStream.beginText();
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
            contentStream.newLineAtOffset(50, 750);
            contentStream.showText("Lista de Tarefas - ToDoApp");
            contentStream.endText();

            // Exemplo de tarefas (substitua pela lista real do walking skeleton)
            List<String> tasks = List.of("Tarefa 1: Comprar leite", "Tarefa 2: Reunião com equipe", "Tarefa 3: Atualizar relatório");

            float yPosition = 700;
            for (String task : tasks) {
                contentStream.beginText();
                contentStream.setFont(PDType1Font.HELVETICA, 12);
                contentStream.newLineAtOffset(50, yPosition);
                contentStream.showText(task);
                contentStream.endText();
                yPosition -= 20;
            }

            contentStream.close();
            document.save("tarefas.pdf");
            document.close();

            // Download (em Vaadin, use StreamResource para download)
            // Para simplicidade, salve localmente; integre com Vaadin FileDownloader se necessário
            System.out.println("PDF gerado: tarefas.pdf");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}