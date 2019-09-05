package com.secured.auth

class MailingContext
{
    private MailingStrategy strategy
    MailingContext(MailingStrategy strategy)
    {
        this.strategy = strategy
    }
    def executeStrategy(Mail mail) throws Exception
    {
            strategy.sendEmail(mail)
    }

}
