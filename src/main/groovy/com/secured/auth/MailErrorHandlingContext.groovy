package com.secured.auth

class MailErrorHandlingContext
{
    private MailErrorHandlingStrategy handler
    MailErrorHandlingContext(MailErrorHandlingStrategy handler)
    {
        this.handler = handler
    }
    def executeStrategy(Mail mail)
    {
        handler.handleErrors(mail)
    }
}
