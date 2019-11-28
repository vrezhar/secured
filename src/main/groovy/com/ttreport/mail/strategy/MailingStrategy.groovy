package com.ttreport.mail.strategy

import com.ttreport.mail.Mail

interface MailingStrategy
{
    def sendEmail(Mail mail) throws Exception
}
