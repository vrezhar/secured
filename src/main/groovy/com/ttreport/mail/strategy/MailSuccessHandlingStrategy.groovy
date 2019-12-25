package com.ttreport.mail.strategy

interface MailSuccessHandlingStrategy {
    def handleSuccess(Closure closure)
}