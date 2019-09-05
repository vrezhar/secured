package com.secured.auth

interface MailErrorHandlingStrategy
{
    def handleErrors(Mail mail,Exception e)
}
