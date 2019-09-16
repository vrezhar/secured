package com.secured.strategy

import com.secured.Mail

interface MailErrorHandlingStrategy
{
    def handleErrors(Mail mail, Exception e)
}
