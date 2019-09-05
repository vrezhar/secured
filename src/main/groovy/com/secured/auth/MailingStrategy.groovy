package com.secured.auth



interface MailingStrategy
{
    def sendEmail(Mail mail) throws Exception
}
