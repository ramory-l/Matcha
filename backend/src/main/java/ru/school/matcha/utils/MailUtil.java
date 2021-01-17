package ru.school.matcha.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import ru.school.matcha.exceptions.MailException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

@Slf4j
public class MailUtil {
    private static final String MESSAGE_CONTENT_TYPE = "text/html; charset=utf-8";
    private static final Session session;

    private MailUtil() {
    }

    static {
        Properties properties;
        try {
            properties = Resources.getResourceAsProperties("mail.properties");
            session = Session.getDefaultInstance(properties);
            Credentials.USERNAME = properties.getProperty("mail.username");
            Credentials.PASSWORD = properties.getProperty("mail.password");
            Credentials.SMTPS_USER = properties.getProperty("mail.smtps.user");
        } catch (IOException e) {
            log.error("Failed to read properties for MailUtil");
            throw new MailException(e.getMessage());
        }
    }

    public static void send(String to, String subject, String text) {
        MimeMessage message = createMimeMessage(to, subject, text);
        try {
            Transport transport = session.getTransport();
            transport.connect(Credentials.USERNAME, Credentials.PASSWORD);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            throw new MailException("Failed to send email");
        }
    }

    private static MimeMessage createMimeMessage(String to, String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(Credentials.SMTPS_USER));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(text, MESSAGE_CONTENT_TYPE);
            return message;
        } catch (MessagingException ex) {
            throw new MailException("Failed to create message for email");
        }
    }

    private static class Credentials {
        private static String USERNAME;
        private static String PASSWORD;
        private static String SMTPS_USER;
    }
}
