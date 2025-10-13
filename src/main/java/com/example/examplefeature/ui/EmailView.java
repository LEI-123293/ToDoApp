package com.example.examplefeature.ui;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.EmailField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

import org.apache.commons.mail.EmailException;

import com.vaadin.flow.component.html.Main;
import com.vaadin.flow.router.Menu;
import com.example.examplefeature.EmailSendler;

@Route("email")
@PageTitle("Enviar Email")
@Menu(order = 1, icon = "vaadin:envelope", title = "Email")
public class EmailView extends Main {

    public EmailView() {
        setSizeFull();
        addClassName("email-view");

        // --- Formulário de envio de email ---
        EmailField toField = new EmailField("Destinatário (Email)");
        TextField subjectField = new TextField("Assunto");
        TextArea messageArea = new TextArea("Mensagem");
        messageArea.setWidth("400px");
        messageArea.setHeight("150px");

        Button sendButton = new Button("Enviar Email");
        sendButton.addClickListener(e -> {
            try {
                String to = toField.getValue();
                String subject = subjectField.getValue();
                String message = messageArea.getValue();

                if (to.isEmpty() || subject.isEmpty() || message.isEmpty()) {
                    Notification.show("Por favor preencha todos os campos!");
                    return;
                }

                EmailSendler.sendEmail(to, subject, message);
                Notification.show("✅ Email enviado com sucesso!");

                // 🔄 Limpar os campos após envio bem-sucedido
                toField.clear();
                subjectField.clear();
                messageArea.clear();

            } catch (EmailException ex) {
                Notification.show("Erro: " + ex.getMessage());
            }
        });

        // ✅ ADICIONA O FORMULÁRIO AO LAYOUT (sem isto, a página fica em branco)
        FormLayout emailForm = new FormLayout(toField, subjectField, messageArea, sendButton);
        add(emailForm);
    }
}

