package com.ttreport.mail

import com.ttreport.mail.strategy.MailErrorHandlingStrategy
import com.ttreport.mail.strategy.MailingStrategy
import com.ttreport.mail.strategy.handlers.RejectEmail
import com.ttreport.mail.strategy.senders.SendViaGmail

class Mail extends MailingConfigurationAware
{
    String from
    String to
    String subject
    String text
    MailingStrategy strategy
    MailErrorHandlingStrategy handler
    def printConfiguration()
    {
        println(mailConfigs)
        return this
    }
    def useSendingStrategy(MailingStrategy strategy)
    {
        this.strategy = strategy
        return this
    }

    def onErrors(MailErrorHandlingStrategy handler)
    {
        this.handler = handler
        return this
    }
    def fromDefaultSender()
    {
        from = mailConfigs.default_sender
        return this
    }
    def from(String senderEmail)
    {
        from = senderEmail
        return this
    }
    def to(String receiverEmail)
    {
        to = receiverEmail
        return this
    }
    def withSubject(String subject = "Verify Your email address")
    {
        this.subject = subject
        return this
    }

    def withMessage(String text)
    {
        this.text = text
        return this
    }
    def send(String username = mailConfigs.gmail_account, String password = mailConfigs.gmail_password)
    {
        try{
            if(!strategy)
                strategy = SendViaGmail.usingAccount(username,password)
            strategy.sendEmail(this)
            return this
        }
        catch(Exception e)
        {
            if(!handler)
                handler = RejectEmail.withMessage(e.message)
            handler.handleErrors(this,e)
            return this
        }
    }
}
