package com.example.examplefeature.ui;

import com.example.examplefeature.Task;
import com.example.examplefeature.TaskService;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.*;
import com.vaadin.flow.component.notification.Notification;

@Route("tasks/:taskId")
@PageTitle("Detalhes da Tarefa")
public class TaskDetailsView extends VerticalLayout implements BeforeEnterObserver {

    private final TaskService taskService;

    public TaskDetailsView(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        Long taskId = event.getRouteParameters().getLong("taskId").orElse(null);

        if (taskId == null) {
            Notification.show("ID de tarefa inválido!");
            event.forwardTo(QRCodeView.class);
            return;
        }

        taskService.findById(taskId).ifPresentOrElse(
                this::showTask,
                () -> {
                    Notification.show("Tarefa não encontrada!");
                    event.forwardTo(QRCodeView.class);
                }
        );
    }


    private void showTask(Task task) {
        removeAll();
        add(
                new H2("Detalhes da Tarefa"),
                new Paragraph("Descrição: " + task.getDescription()),
                new Paragraph("Data limite: " +
                        (task.getDueDate() != null ? task.getDueDate() : "Sem data")),
                new Paragraph("Criada em: " + task.getCreationDate())
        );
    }
}
