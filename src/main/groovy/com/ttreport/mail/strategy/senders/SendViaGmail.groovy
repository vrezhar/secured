package com.ttreport.mail.strategy.senders

import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.mail.Mail
import com.ttreport.mail.MailingConfiguration
import com.ttreport.mail.strategy.MailingStrategy

import javax.mail.*
import javax.mail.internet.*

class SendViaGmail extends ApplicationConfiguration implements MailingStrategy
{
    private String username
    private String password

    private SendViaGmail(String username = null, String password = null)
    {
        this.username = username?: mailConfigs.sender as String
        this.password = password?: mailConfigs.password as String
    }

    public static SendViaGmail usingAccount(String username, String password)
    {
        SendViaGmail sender = new SendViaGmail(username,password)
        return sender
    }

    public static SendViaGmail usingDefaultAccount()
    {
        SendViaGmail sender = new SendViaGmail()
        return sender
    }

    @Override
    def sendEmail(Mail mail) throws Exception
    {
        String host = "smtp.gmail.com"
        Properties props = new Properties()
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", host)
        props.put("mail.smtp.user", username)
        props.put("mail.smtp.password", password)
        props.put("mail.smtp.port", "587")
        props.put("mail.smtp.auth", "true")

        Session session = Session.getDefaultInstance(props, null)
        /*
        Session session = Session.getDefaultInstance(props,
                new Authenticator() {
                    protected PasswordAuthentication getPasswordAuthentication() {
                        return new PasswordAuthentication(username,password);
                    }
                });
        */

        MimeMessage message = new MimeMessage(session)
        message.setContent(mail.text,"text/html; charset=utf-8")
        message.setFrom(new InternetAddress(username))
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail.to))
        message.setSubject(mail.subject)

        Transport transport = session.getTransport("smtp")
        transport.connect(host, username, password)

        transport.sendMessage(message, message.getAllRecipients())
        transport.close()
    }

}
