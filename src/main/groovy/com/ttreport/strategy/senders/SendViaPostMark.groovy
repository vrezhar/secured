package com.ttreport.strategy.senders

import com.ttreport.Mail
import com.ttreport.strategy.MailingStrategy

class SendViaPostMark implements MailingStrategy
{

    private static final username = "username"
    private static final password = "password"
    private static final server_token = "s_token"
    private static final account_token = "a_token"
    @Override
    def sendEmail(Mail mail) throws Exception {
        return null
    }
}
