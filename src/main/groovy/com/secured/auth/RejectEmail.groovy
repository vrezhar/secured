package com.secured.auth

import grails.events.annotation.Publisher

class RejectEmail implements MailErrorHandlingStrategy
{
    private String errorMessage
    private RejectEmail(String message)
    {
        errorMessage = message
    }
    static RejectEmail withMessage(String message = "Something wrong with entered email")
    {
        return new RejectEmail(message)
    }
    @Override
    @Publisher('UnableToVerifyEmail')
    def handleErrors(Mail mail) {
        return errorMessage
    }
}
