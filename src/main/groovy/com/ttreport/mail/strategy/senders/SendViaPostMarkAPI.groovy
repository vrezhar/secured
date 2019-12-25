package com.ttreport.mail.strategy.senders

import com.ttreport.configuration.ApplicationConfiguration
import com.ttreport.logs.ServerLogger
import com.ttreport.mail.Mail
import com.ttreport.mail.strategy.MailingStrategy
import groovy.json.JsonBuilder

import javax.net.ssl.HttpsURLConnection

//import com.wildbit.java.postmark.Postmark
//import com.wildbit.java.postmark.client.ApiClient
//import com.wildbit.java.postmark.client.data.model.message.Message
//import com.wildbit.java.postmark.client.data.model.message.MessageResponse

class SendViaPostMarkAPI extends ApplicationConfiguration implements MailingStrategy
{
    private String server_token

    SendViaPostMarkAPI(String token)
    {
        this.server_token = token
    }

    @Override
    def sendEmail(Mail mail) throws IOException,SocketTimeoutException,Exception {
//        ApiClient client = Postmark.getApiClient(server_token)
//        Message message = new Message(mail.from,mail.to,mail.subject,mail.text)
//        MessageResponse response = client.deliverMessage(message);
        HttpURLConnection connection
        try{
            URL url = new URL('https://api.postmarkapp.com/email')
            connection = (HttpsURLConnection)url.openConnection()
            connection.setRequestMethod("POST")
            connection.setRequestProperty("Accept", "application/json")
            connection.setRequestProperty("Content-Type", "application/json")
            connection.setRequestProperty("X-Postmark-Server-Token", server_token)
            connection.setDoOutput(true)
            DataOutputStream wr = new DataOutputStream (connection.getOutputStream())
            String payload = new JsonBuilder(
                    [
                            From: mail.from,
                            To: mail.to,
                            Subject: mail.subject,
                            HtmlBody: mail.text
                    ]
            ).toString()
            ServerLogger.log(payload)
            wr.writeBytes(payload)
            InputStream is = connection.getInputStream()
            BufferedReader rd = new BufferedReader(new InputStreamReader(is))
            StringBuilder response = new StringBuilder()
            String line
            while ((line = rd.readLine()) != null) {
                response.append(line)
                response.append(System.lineSeparator())
            }
            rd.close()
            if(response[response.length() - 1] == '\n'){
                response.deleteCharAt(response.length() - 1)
            }
            println(response)
        }
        catch (SocketTimeoutException socketTimeoutException){
            ServerLogger.log("Socket timeout exception occurred while sending the request")
            ServerLogger.log_exception(socketTimeoutException)
            throw socketTimeoutException
        }
        catch(IOException ioException) {
            ServerLogger.log("input-output exception occurred while sending the request")
            ServerLogger.log_exception(ioException)
            if(connection){
                ServerLogger.log(connection.responseMessage)
                ServerLogger.log(connection.toString())
            }
            throw ioException
        }
        catch(Exception e) {
            ServerLogger.log("unknown exception occurred while sending the request")
            ServerLogger.log_exception(e)
            if(connection){
                ServerLogger.log(connection.responseMessage)
            }
            throw e
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }

    public static SendViaPostMarkAPI usingToken(String token = mailConfigs.postmark_token)
    {
        SendViaPostMarkAPI sender = new SendViaPostMarkAPI(token)
        return sender
    }

    public static SendViaPostMarkAPI usingDefaultToken()
    {
        SendViaPostMarkAPI sender = new SendViaPostMarkAPI(mailConfigs.postmark_token as String)
        return sender
    }
}
