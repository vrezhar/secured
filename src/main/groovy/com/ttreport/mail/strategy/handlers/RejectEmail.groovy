package com.ttreport.mail.strategy.handlers

import com.ttreport.mail.Mail
import com.ttreport.mail.strategy.MailErrorHandlingStrategy

class RejectEmail implements MailErrorHandlingStrategy
{
    private String errorMessage
    private RejectEmail(String message)
    {
        errorMessage = message
    }
    static RejectEmail withMessage(String message = null)
    {
        return new RejectEmail(message)
    }
    static RejectEmail withDefaultMessage()
    {
        return new RejectEmail(null)
    }
    @Override
    def handleErrors(Mail mail, Exception e)
    {
        if(!errorMessage){
            errorMessage = e.message
        }
        println(errorMessage)
        return errorMessage
    }
}
