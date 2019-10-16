package com.ttreport.strategy

import com.ttreport.Mail

interface MailingStrategy
{
    def sendEmail(Mail mail) throws Exception
}
