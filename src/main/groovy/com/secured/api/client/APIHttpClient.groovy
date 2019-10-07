package com.secured.api.client

import com.secured.logs.DevCycleLogger

import javax.net.ssl.HttpsURLConnection

class APIHttpClient
{
     static String sendRequest(String url_, String params, String method = "POST", String content_type = "application/json")
     {

         URL url = new URL(url_)
         HttpURLConnection connection = (HttpURLConnection)url.openConnection()
         try {
             connection.setRequestMethod(method)
             connection.setRequestProperty("Content-Type", content_type)
             connection.setRequestProperty("Content-Length", Integer.toString(params.getBytes().length))
             connection.setDoOutput(true)

             DataOutputStream wr = new DataOutputStream (
                     connection.getOutputStream())
             wr.writeBytes(params)
             wr.close()

             InputStream is = connection.getInputStream()
             BufferedReader rd = new BufferedReader(new InputStreamReader(is))
             StringBuilder response = new StringBuilder()
             String line
             while ((line = rd.readLine()) != null) {
                 response.append(line)
                 response.append('\r')
             }
             rd.close()
             return response.toString()
         }
         catch(IOException ioexception)
         {
             DevCycleLogger.log("input-output exception occurred when processing the request")
             ioexception.printStackTrace()
             return null
         }

         catch(Exception e) {
             return  null
         }
         finally {
            if(connection != null){
                connection.disconnect()
            }
         }

     }
}