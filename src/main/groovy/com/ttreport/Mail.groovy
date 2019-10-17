package com.ttreport

import com.ttreport.strategy.MailErrorHandlingStrategy
import com.ttreport.strategy.MailingStrategy
import com.ttreport.strategy.handlers.RejectEmail
import com.ttreport.strategy.senders.SendViaGmail
import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class Mail implements GrailsConfigurationAware
{
    private Map<String,String> conf
    String from
    String to
    String subject
    String text
    MailingStrategy strategy
    MailErrorHandlingStrategy handler
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
        from = conf.email
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
    def send(String username = conf.gmail_email, String password = conf.gmail_password)
    {
        try{
            if(!strategy)
                strategy = SendViaGmail.usingAccount(username,password)
            strategy.sendEmail(this)
        }
        catch(Exception e)
        {
            if(!handler)
                handler = RejectEmail.withMessage(e.message)
            handler.handleErrors(this,e)
        }
    }

    @Override
    void setConfiguration(Config co) {
        conf.email = co.mail.sender.email
        conf.gmail_email = co.mail.gmail.email
        conf.gmail_password = co.mail.gmail.password
    }
}
