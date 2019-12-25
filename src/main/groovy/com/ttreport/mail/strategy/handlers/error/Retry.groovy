package com.ttreport.mail.strategy.handlers.error

import com.ttreport.logs.ServerLogger
import com.ttreport.mail.Mail
import com.ttreport.mail.strategy.MailErrorHandlingStrategy
import com.ttreport.mail.strategy.MailingStrategy

class Retry implements MailErrorHandlingStrategy
{

    private MailingStrategy retryStrategy
    private MailErrorHandlingStrategy repeatedFailureHandler
    private Retry(MailingStrategy retryStrategy)
    {
        this.retryStrategy = retryStrategy
        repeatedFailureHandler = RejectEmail.withMessage("Failed to verify email twice")
    }
    static Retry withStrategy(MailingStrategy strategy)
    {
        return new Retry(strategy)
    }
    def ifFailedAgain(MailErrorHandlingStrategy handler)
    {
        repeatedFailureHandler = handler
    }
    @Override
    def handleErrors(Mail mail, Exception e) {
        try{
            //do something with e
            println("Sending email failed due to exception")
            ServerLogger.log_exception(e)
            ServerLogger.print_logs()
            ServerLogger.cleanup()
            retryStrategy.sendEmail(mail)
        }
        catch(Exception again)
        {
            ServerLogger.log_exception(again)
            ServerLogger.print_logs()
            ServerLogger.cleanup()
            repeatedFailureHandler.handleErrors(mail,again)
        }
    }
}
