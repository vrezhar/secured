package com.ttreport.mail

import com.ttreport.mail.strategy.MailErrorHandlingStrategy
import com.ttreport.mail.strategy.MailingStrategy
import com.ttreport.mail.strategy.handlers.error.RejectEmail
import com.ttreport.mail.strategy.senders.SendViaGmail

class Mail
{
    String from
    String to
    String subject
    String text
    MailingStrategy strategy
    Closure handleSuccess
    Closure handleErrors
    def useSendingStrategy(MailingStrategy strategy)
    {
        this.strategy = strategy
        return this
    }

    def onErrors(Closure handler)
    {
        handleErrors = handler
        return this
    }
    def onSuccess(Closure closure)
    {
        handleSuccess = closure
        return this
    }
    def fromDefaultSender()
    {
        from = "bronsmailsupreme@gmail.com"
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
    def send(String username = "bronsmailsupreme@gmail.com", String password = "bruhMoment")
    {
        try{
            if(!strategy)
                strategy = SendViaGmail.usingAccount(username,password)
            strategy.sendEmail(this)
            if(!handleSuccess){
                handleSuccess = { ->
                    println("No action defined handling successful mail delivery")
                }
            }
            handleSuccess()
            return this
        }
        catch(Exception e)
        {
            if(!handleErrors)
                handleErrors = { Mail mail, Exception exception ->
                    RejectEmail.withMessage(exception.message).handleErrors(mail, exception)
                    return null
                }
            handleErrors(this,e)
            return this
        }
    }
}
