package com.ttreport.strategy

import com.ttreport.Mail

interface MailErrorHandlingStrategy
{
    def handleErrors(Mail mail, Exception e)
}
