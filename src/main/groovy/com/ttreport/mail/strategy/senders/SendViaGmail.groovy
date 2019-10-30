package com.ttreport.mail.strategy.senders

import com.ttreport.mail.Mail
import com.ttreport.mail.MailingConfigurationAware
import com.ttreport.mail.strategy.MailingStrategy

import javax.mail.*;
import javax.mail.internet.*;

class SendViaGmail extends MailingConfigurationAware implements MailingStrategy
{
    private String username
    private String password

    private SendViaGmail(String username, String password)
    {
        this.username = username
        this.password = password
    }

    public static SendViaGmail usingAccount(String username, String password)
    {
        SendViaGmail sender = new SendViaGmail(username,password)
        return sender
    }

    public static SendViaGmail usingDefaultAccount()
    {
        SendViaGmail sender = new SendViaGmail(mailConfigs.gmail_account,mailConfigs.gmail_password)
        return sender
    }

    @Override
    def sendEmail(Mail mail) throws Exception
    {
        String host = "smtp.gmail.com";
        Properties props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props, null);
        /*
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        */

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(username));
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail.to));
        message.setSubject(mail.subject);
        message.setText(mail.text);

        Transport transport = session.getTransport("smtp");
        transport.connect(host, username, password);

        transport.sendMessage(message, message.getAllRecipients());
        transport.close();
    }

}
