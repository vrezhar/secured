package com.secured.auth

class Mail
{
    String from
    String to
    String subject
    String text
    MailingContext context
    MailErrorHandlingContext handler
    def useSendingStrategy(MailingStrategy strategy)
    {
        context = new MailingContext(strategy)
        return this
    }
    def onErrors(MailErrorHandlingStrategy handler)
    {
        this.handler = new MailErrorHandlingContext(handler)
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
            context.executeStrategy(this)
        }
        catch(Exception e)
        {
            handler.executeStrategy(this)
        }
    }
}
