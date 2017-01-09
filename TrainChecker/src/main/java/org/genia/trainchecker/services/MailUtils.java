package org.genia.trainchecker.services;


import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

class MailUtils {
    private static final Properties smtpProperties = new Properties();

    static {
        smtpProperties.put("mail.smtp.host", "smtp.gmail.com");
        smtpProperties.put("mail.smtp.socketFactory.port", "465");
        smtpProperties.put("mail.smtp.socketFactory.class",
                "javax.net.ssl.SSLSocketFactory");
        smtpProperties.put("mail.smtp.auth", "true");
        smtpProperties.put("mail.smtp.port", "465");
    }

    static void sendEmail(final Properties credentials, String to, String subject, String body) {
        sendEmail(credentials, to, subject, body, false);
    }

    static void sendEmail(final Properties credentials, String to, String subject, String body, boolean isHtml) {

        Session session = Session.getDefaultInstance(smtpProperties,
            new javax.mail.Authenticator() {
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(credentials.getProperty("username"),
                            credentials.getProperty("password"));
                }
            });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("es.terminchecker@gmail.com"));
            message.setRecipients(Message.RecipientType.TO,
                    InternetAddress.parse(to));
            message.setSubject(subject);
            if (isHtml) {
                Multipart multipart = new MimeMultipart();
                MimeBodyPart messageBodyPart = new MimeBodyPart();
                messageBodyPart.setContent(body, "text/html;charset=utf-8");
                multipart.addBodyPart(messageBodyPart);
                message.setContent(multipart);
            } else {
                message.setText(body);
            }


            Transport.send(message);

            System.out.println("Email was sent to " + to);

        } catch (MessagingException e) {
//			LoggerUtil.logError("Error while sending email. " + e.getMessage());
//			throw new RuntimeException(e);
        }
    }
}
