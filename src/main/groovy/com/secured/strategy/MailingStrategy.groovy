package com.secured.strategy

import com.secured.Mail

interface MailingStrategy
{
    def sendEmail(Mail mail) throws Exception
}
