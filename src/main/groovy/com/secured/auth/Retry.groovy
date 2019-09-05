package com.secured.auth

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
    def handleErrors(Mail mail,Exception e) {
        try{
            //do something with e
            println("Sending email failed due to exception: " + e.message)
            retryStrategy.sendEmail(mail)
        }
        catch(Exception again)
        {
            repeatedFailureHandler.handleErrors(mail,again)
        }
    }
}
