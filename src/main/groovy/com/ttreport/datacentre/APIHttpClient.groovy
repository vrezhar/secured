package com.ttreport.datacentre

import com.ttreport.data.Document
import com.ttreport.logs.DevCycleLogger
import grails.converters.JSON

import javax.net.ssl.HttpsURLConnection

class APIHttpClient
{
    String data
    String content_type = "application/json"
    String method = "POST"
    String targetUrl

    String sendHttpRequest(String url_ = targetUrl, String params = this.data, String method = this.method, String content_type = this.content_type, boolean usehttps = false)
    {
        if(!url_ || !params) {
            throw new Exception("Crucial parameters not specified")
        }
        URL url = new URL(url_)
        HttpURLConnection connection
        if(!usehttps) {
            connection = (HttpsURLConnection)url.openConnection()
        }
        else{
            connection = (HttpURLConnection)url.openConnection()
        }
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
                response.append('\n')
            }
            rd.close()
            return response.toString()
        }
        catch(IOException ioexception) {
            DevCycleLogger.log("input-output exception occurred when processing the request")
            ioexception.printStackTrace()
            return null
        }
        catch(Exception e) {
            DevCycleLogger.log("unknown exception occurred when processing the request")
            e.printStackTrace()
            return  null
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }

    static String sendRequest(Document document)
    {
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (document.requestType == "ACCEPT") ? "/test1" : "/test2"
        client.data = new JSON(document).toString()
        return client.sendHttpRequest()
    }



}