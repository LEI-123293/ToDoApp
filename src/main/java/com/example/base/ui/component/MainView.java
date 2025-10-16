package com.example.base.ui.component;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.example.base.ui.MainLayout;

@Route(value = "", layout = MainLayout.class)
public class MainView extends VerticalLayout {
    public MainView() {
        add("Bem-vindo à ToDoApp! Lista de tarefas virá aqui."); // Placeholder
    }
}
