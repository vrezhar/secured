package com.secured.strategy.handlers

import com.secured.Mail
import com.secured.strategy.MailErrorHandlingStrategy

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
    @Override
    //@Publisher('UnableToVerifyEmail')
    def handleErrors(Mail mail, Exception e) {
        if(!errorMessage) errorMessage = e.message
        println(errorMessage)
        return errorMessage
    }
}
