package com.Clinic_Accounting_System.utils;

import lombok.extern.log4j.Log4j2;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;

@Log4j2
public class MailBot {

    private static MailBot instance;
    private static Session session;
    private static final Properties properties = new Properties();

    private MailBot() {
        try(InputStream resourceAsStream = MailBot.class.getResourceAsStream("/mail.properties")) {
            properties.load(resourceAsStream);
            session = Session.getDefaultInstance(properties, new Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(properties.getProperty("MAILBOX_ADDRESS"),
                                                    properties.getProperty("MAILBOX_PASSWORD"));
                }
            });
        } catch(IOException e) {
            log.error("MailBot exception: " + e.getMessage());
        }
    }

    public static synchronized MailBot getInstance() {
        if(instance == null)
            instance = new MailBot();
        return instance;
    }

    public boolean sendMessage(String recipient, String subject, String body) {
        try {
            MimeMessage msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(properties.getProperty("MAILBOX_ADDRESS"), "No reply"));
            msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipient, false));
            msg.setSentDate(new Date());
            msg.setSubject(subject, "UTF-8");
            msg.setText(body, "UTF-8");
            Transport.send(msg);
            return true;
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("MailBot exception: " + e.getMessage());
            return false;
        }
    }

}
