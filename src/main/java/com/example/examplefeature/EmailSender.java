package com.example.examplefeature;

import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.SimpleEmail;

public class EmailSender {

    // ⚙️ Configurações de SMTP — substituir pelos teus dados
    private static final String SMTP_HOST = "smtp.gmail.com";
    private static final int SMTP_PORT = 587;
    private static final String SMTP_USER = "ricardomilosfortnite5@gmail.com";     // o teu email
    private static final String SMTP_PASS = "qumg nrpx uris yizk";       // App Password do Gmail

    public static void sendEmail(String to, String subject, String message) throws EmailException {
        SimpleEmail email = new SimpleEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        email.setAuthenticator(new DefaultAuthenticator(SMTP_USER, SMTP_PASS));
        email.setStartTLSEnabled(true);
        email.setFrom(SMTP_USER);
        email.setSubject(subject);
        email.setMsg(message);
        email.addTo(to);
        email.send();
    }
}
