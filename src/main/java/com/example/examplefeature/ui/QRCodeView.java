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


import java.io.IOException;
import java.util.Optional;

@Route("qrcode")
@PageTitle("Task QR Codes")
@Menu(order = 2, icon = "vaadin:qrcode", title = "QR Codes")
public class QRCodeView extends Main {

    private final TaskService taskService;
    private final Grid<Task> taskGrid;

    public QRCodeView(TaskService taskService) {
        this.taskService = taskService;
        this.taskGrid = new Grid<>(Task.class, false);

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
            String text = "Task: " + task.getDescription()
                    + "\nDue: " + (task.getDueDate() != null ? task.getDueDate() : "Never")
                    + "\nCreated: " + task.getCreationDate();

            String qrCodeBase64 = QrCodeGenerator.generateQRCode(text, 250, 250);
            Image qrImage = new Image(qrCodeBase64, "QR Code");

            Dialog dialog = new Dialog();
            dialog.add(qrImage);
            Button close = new Button("Fechar", e -> dialog.close());
            dialog.add(close);
            dialog.open();

        } catch (WriterException | IOException e) {
            Notification.show("Erro ao gerar QR: " + e.getMessage());
        }
    }
}
