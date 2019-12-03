package com.ttreport.datacenter


import com.ttreport.logs.ServerLogger

import javax.net.ssl.HttpsURLConnection
import java.util.concurrent.locks.ReentrantLock

class APIHttpClient
{
    private static String token
    private static final ReentrantLock tokenLock = new ReentrantLock()

    String data
    String content_type = "application/json"
    String method = "POST"
    String targetUrl
    int timeout = 10000

    static String getToken()
    {
        return token
    }
    static void setToken(String s)
    {
        token = s
    }

    static void acquireTokenLock()
    {
        tokenLock.lock()
    }

    static void releaseTokenLock()
    {
        tokenLock.unlock()
    }


    String sendHttpRequest(String url_ = this.targetUrl, String data_ = this.data, String method = this.method, String content_type = this.content_type, int timeout = this.timeout, boolean usehttps = false)
    throws IOException,SocketTimeoutException,Exception
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
            if(timeout){
                connection.setConnectTimeout(timeout)
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
            if(response[response.length() - 1] == '\n'){
                response.deleteCharAt(response.length() - 1)
            }
            return response.toString()
        }
        catch (SocketTimeoutException socketTimeoutException){
            ServerLogger.log("Socket timeout exception occurred while sending the request")
            ServerLogger.log_exception(socketTimeoutException)
            throw socketTimeoutException
        }
        catch(IOException ioException) {
            ServerLogger.log("input-output exception occurred while sending the request")
            ServerLogger.log_exception(ioException)
            throw ioException
        }
        catch(Exception e) {
            ServerLogger.log("unknown exception occurred while sending the request")
            ServerLogger.log_exception(e)
            throw e
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }


}