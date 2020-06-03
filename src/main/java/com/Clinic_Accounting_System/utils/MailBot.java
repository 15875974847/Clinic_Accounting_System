package com.Clinic_Accounting_System.utils;

import lombok.extern.log4j.Log4j2;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@Log4j2
public class MailBot {

    private static MailBot instance;
    private static Session session;
    private static final String FROM = "smtp.server.arti.cas@mail.ru";
    private static final String PASSWORD = "simplePass";

    private MailBot() {
        Properties properties = new Properties();
        properties.put("mail.smtp.host", "smtp.mail.ru");
        properties.put("mail.smtp.port", "465");
        properties.put("mail.smtp.user", "Arti Tsv");
        session = Session.getInstance(properties, null);
    }

    public static synchronized MailBot getInstance() {
        if(instance == null)
            instance = new MailBot();
        return instance;
    }

    public boolean sendMessage(String recipient, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(FROM);
            msg.setRecipients(Message.RecipientType.TO, recipient);
            msg.setSubject(subject);
            msg.setSentDate(new Date());
            msg.setText(body);
            Transport.send(msg, FROM, PASSWORD);
            return true;
        } catch (MessagingException e) {
            log.error("Messaging exception caught!" + e);
            return false;
        }
    }

}
