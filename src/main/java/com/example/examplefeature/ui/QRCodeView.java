package com.example.examplefeature.ui;

import com.example.examplefeature.QrCodeGenerator;
import com.example.examplefeature.Task;
import com.example.examplefeature.TaskService;
import com.google.zxing.WriterException;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.Menu;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.springframework.data.domain.PageRequest;
import com.vaadin.flow.component.html.Anchor;


import java.io.IOException;
import java.util.Optional;

@Route("qrcode")
@PageTitle("Task QR Codes")
@Menu(order = 2, icon = "vaadin:qrcode", title = "QR Codes")
public class QRCodeView extends Main {

    public QRCodeView(TaskService taskService) {
        Grid<Task> taskGrid = new Grid<>(Task.class, false);

        // Configuração da tabela
        taskGrid.setItems(query -> taskService.list(PageRequest.of(query.getPage(), query.getPageSize())).stream());
        taskGrid.addColumn(Task::getDescription).setHeader("Description");
        taskGrid.addColumn(task -> Optional.ofNullable(task.getDueDate())
                        .map(Object::toString).orElse("Never"))
                .setHeader("Due Date");
        taskGrid.addColumn(task -> task.getCreationDate().toString()).setHeader("Creation Date");

        // Coluna com botão para gerar QR
        taskGrid.addComponentColumn(task -> {
            Button qrButton = new Button("Ver QR");
            qrButton.addClickListener(e -> showQrDialog(task));
            return qrButton;
        }).setHeader("QR Code");

        add(taskGrid);
        setSizeFull();
    }

    private void showQrDialog(Task task) {
        try {
            // Gera o link da tarefa (podes mudar o domínio conforme o deploy)
            String link = "http://localhost:8080/tasks/" + task.getId();

            // Texto adicional (opcional)
            String text = "Abrir tarefa:\n" + link;

            // Gera o QR code a partir do link
            String qrCodeBase64 = QrCodeGenerator.generateQRCode(link, 250, 250);

            Image qrImage = new Image(qrCodeBase64, "QR Code");

            Dialog dialog = new Dialog();
            dialog.add(qrImage);

            // Mostra também o link como texto clicável
            Anchor anchor = new Anchor(link, "Abrir tarefa no browser");
            anchor.setTarget("_blank");
            dialog.add(anchor);

            Button close = new Button("Fechar", e -> dialog.close());
            dialog.add(close);

            dialog.open();

        } catch (Exception e) {
            Notification.show("Erro ao gerar QR: " + e.getMessage());
        }
    }

}
