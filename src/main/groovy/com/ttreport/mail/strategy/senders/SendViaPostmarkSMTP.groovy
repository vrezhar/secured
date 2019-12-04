package com.ttreport.mail.strategy.senders

import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.mail.Mail
import com.ttreport.mail.MailingConfiguration
import com.ttreport.mail.strategy.MailingStrategy

import javax.mail.Authenticator
import javax.mail.Message
import javax.mail.PasswordAuthentication
import javax.mail.Session
import javax.mail.Transport
import javax.mail.internet.InternetAddress
import javax.mail.internet.MimeMessage

class SendViaPostmarkSMTP extends ApplicationConfiguration implements MailingStrategy
{
    private String username
    private String password

    private SendViaPostmarkSMTP(String username, String password)
    {
        this.username = username
        this.password = password
    }

    public static SendViaPostmarkSMTP usingAccount(String username, String password)
    {
        SendViaPostmarkSMTP sender = new SendViaPostmarkSMTP(username,password)
        return sender
    }

    public static SendViaPostmarkSMTP usingDefaultAccount()
    {
        SendViaPostmarkSMTP sender = new SendViaPostmarkSMTP("9ba6aac3-8969-41cd-bf38-bd56d588eb9c","9ba6aac3-8969-41cd-bf38-bd56d588eb9c")
        return sender
    }

    @Override
    def sendEmail(Mail mail) throws Exception
    {
        String host = "smtp.postmarkapp.com"
        Properties props = new Properties()
        props.put("mail.smtp.starttls.enable", "true")
        props.put("mail.smtp.host", host)
        props.put("mail.smtp.auth.mechanisms", "CRAM-MD5")
        props.put("mail.smtp.ssl.enable", "true")
//        props.put("mail.smtp.user", username)
//        props.put("mail.smtp.password", password)
        props.put("mail.smtp.port", "2525")
        props.put("mail.smtp.auth", "true")

//        Session session = Session.getDefaultInstance(props, null)

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        })


        MimeMessage message = new MimeMessage(session)
        message.setContent(mail.text,"text/html; charset=utf-8")
        message.setFrom(new InternetAddress(mail.from))
        message.addRecipient(Message.RecipientType.TO,new InternetAddress(mail.to))
        message.setSubject(mail.subject)

        Transport transport = session.getTransport("smtp")
        transport.connect(host, username, password)

        transport.sendMessage(message, message.getAllRecipients())
        transport.close()
    }

}
