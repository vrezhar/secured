package com.ttreport.strategy.senders

import com.ttreport.Mail
import com.ttreport.strategy.MailingStrategy
import com.wildbit.java.postmark.Postmark
import com.wildbit.java.postmark.client.ApiClient
import com.wildbit.java.postmark.client.data.model.message.Message
import com.wildbit.java.postmark.client.data.model.message.MessageResponse
import grails.config.Config
import grails.core.support.GrailsConfigurationAware

class SendViaPostMark implements MailingStrategy, GrailsConfigurationAware
{
    private String server_token
    private static String conf

    SendViaPostMark(String token)
    {
        this.server_token = token?: conf
    }

    @Override
    def sendEmail(Mail mail) throws Exception {
        ApiClient client = Postmark.getApiClient(server_token)
        Message message = new Message(mail.from,mail.to,mail.subject,mail.text)
        MessageResponse response = client.deliverMessage(message);
        return response
    }

    public static SendViaPostMark usingToken(String token)
    {
        SendViaPostMark sender = SendViaPostMark(token)
        return sender
    }

    public static SendViaPostMark usingDefaultToken()
    {
        SendViaPostMark sender = SendViaPostMark(conf)
        return sender
    }

    @Override
    void setConfiguration(Config co) {
        conf = co.mail.postmark.api.token
    }
}
