package com.ttreport.datacenter


import com.ttreport.logs.DevCycleLogger

import javax.net.ssl.HttpsURLConnection

class APIHttpClient
{
    private static String token

    String data
    String content_type = "application/json"
    String method = "POST"
    String targetUrl

    synchronized static String getToken()
    {
        return token
    }
    synchronized static void setToken(String s)
    {
       token = s
    }

    String sendHttpRequest(String url_ = this.targetUrl, String data_ = this.data, String method = this.method, String content_type = this.content_type, boolean usehttps = true)
    throws IOException,Exception
    {
        if(!url_) {
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
            if(content_type) {
                connection.setRequestProperty("Content-Type", content_type)
            }
            if(getToken()){
                connection.setRequestProperty("Authorization", "Bearer " + getToken())
            }
            connection.setDoOutput(true)
            if(data_) {
                connection.setRequestProperty("Content-Length", Integer.toString(data_?.getBytes()?.length))
                DataOutputStream wr = new DataOutputStream (
                        connection.getOutputStream())
                wr.writeBytes(data_)
                wr.close()
            }


            InputStream is = connection.getInputStream()
            BufferedReader rd = new BufferedReader(new InputStreamReader(is))
            StringBuilder response = new StringBuilder()
            String line
            while ((line = rd.readLine()) != null) {
                response.append(line)
                response.append(System.lineSeparator())
            }
            rd.close()
            return response.toString()
        }
        catch(IOException ioexception) {
            DevCycleLogger.log("input-output exception occurred when processing the request")
            ioexception.stackTrace.each {
                DevCycleLogger.log(it.toString())
            }
            return null
        }
        catch(Exception e) {
            DevCycleLogger.log("unknown exception occurred when processing the request")
            e.stackTrace.each {
                DevCycleLogger.log(it.toString())
            }
            return null
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }


}