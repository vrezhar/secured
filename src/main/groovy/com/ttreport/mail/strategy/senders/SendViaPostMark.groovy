package com.ttreport.mail.strategy.senders

import com.ttreport.mail.Mail
import com.ttreport.mail.MailingConfigurationAware
import com.ttreport.mail.strategy.MailingStrategy
//import com.wildbit.java.postmark.Postmark
//import com.wildbit.java.postmark.client.ApiClient
//import com.wildbit.java.postmark.client.data.model.message.Message
//import com.wildbit.java.postmark.client.data.model.message.MessageResponse

class SendViaPostMark extends MailingConfigurationAware implements MailingStrategy
{
    private String server_token

    SendViaPostMark(String token)
    {
        this.server_token = token
    }

    @Override
    def sendEmail(Mail mail) throws Exception {
//        ApiClient client = Postmark.getApiClient(server_token)
//        Message message = new Message(mail.from,mail.to,mail.subject,mail.text)
//        MessageResponse response = client.deliverMessage(message);
        return null
    }

    public static SendViaPostMark usingToken(String token = mailConfigs.postmark_token)
    {
        SendViaPostMark sender = SendViaPostMark(token)
        return sender
    }

    public static SendViaPostMark usingDefaultToken()
    {
        SendViaPostMark sender = SendViaPostMark(mailConfigs.postmark_token)
        return sender
    }
}
