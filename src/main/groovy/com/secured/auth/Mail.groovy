package com.secured.auth

class Mail
{
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
    def send()
    {
        try{
            if(!strategy)
                strategy = new GmailSender()
            strategy.sendEmail(this)
        }
        catch(Exception e)
        {
            if(!handler)
                handler = RejectEmail.withMessage(e.message)
            handler.handleErrors(this,e)
        }
    }
}
