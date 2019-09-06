package com.secured.auth

import java.util.Properties;
import javax.mail.*;
import javax.mail.internet.*;

class GmailSender implements MailingStrategy
{

    final private static String username = "example@gmail.com"
    final private static String password = "SuperSecret"
    @Override
    def sendEmail(Mail mail) throws Exception {
        String host = "smtp.gmail.com";


        String to = mail.to;
        Properties props = new Properties();
        props.put("mail.smtp.host",host);
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
        message.setSubject(mail.subject);
        message.setText(mail.text);

        Transport.send(message);

        }
}
