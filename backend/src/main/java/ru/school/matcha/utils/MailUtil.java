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

    private static Properties properties;
    private static Session mailSession;

    public static void initMail() {
        readProperties();
        openMailSession();
    }

    public static void send(String to, String subject, String text) {
        sendMessage(formingMessage(to, subject, text));
    }

    private static void readProperties() {
        try {
            properties = Resources.getResourceAsProperties("mail.properties");
        } catch (IOException e) {
            throw new MailException(e.getMessage());
        }
    }

    private static void openMailSession() {
        mailSession = Session.getDefaultInstance(properties);
    }

    private static MimeMessage formingMessage(String to, String subject, String text) {
        try {
            MimeMessage message = new MimeMessage(mailSession);
            message.setFrom(new InternetAddress(properties.getProperty("mail.smtps.user")));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setContent(text, "text/html; charset=utf-8");
            return message;
        } catch (MessagingException ex) {
            throw new MailException("Failed to forming message");
        }
    }

    private static void sendMessage(MimeMessage message) {
        try {
            Transport transport = mailSession.getTransport();
            transport.connect(properties.getProperty("mail.username"), properties.getProperty("mail.password"));
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        } catch (MessagingException ex) {
            throw new MailException("Failed to send message");
        }
    }

}
