package com.ttreport.mail.strategy

import com.ttreport.mail.Mail

interface MailErrorHandlingStrategy
{
    def handleErrors(Mail mail, Exception e)
}
