package com.secured.main

import com.secured.Mail
import com.secured.strategy.handlers.RejectEmail
import com.secured.strategy.senders.SendViaGmail
import grails.plugin.springsecurity.annotation.Secured

@Secured(['ROLE_ADMIN'])
class TestController {

    def index()
    {
        try {
            new Mail()
                    .from("vrarakel01@gmail.com")
                    .to("vrarakel01@gmail.com")
                    .withSubject("test")
                    .withMessage("This is a test")
                    .useSendingStrategy(SendViaGmail.usingAccount("username",
                            "password"))
                    .onErrors(RejectEmail.withMessage("Email not sent"))
                    .send()
            render "Success!"
            return
        }
        catch (Exception e)
        {
            render "Failure!"
            return
        }
    }
}
