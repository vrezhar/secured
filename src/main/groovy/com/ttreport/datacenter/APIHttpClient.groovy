package com.ttreport.datacenter


import com.ttreport.logs.DevCycleLogger

import javax.net.ssl.HttpsURLConnection
import java.util.concurrent.atomic.AtomicBoolean
import java.util.concurrent.locks.ReentrantLock

class APIHttpClient
{
    private static String token
    private static final ReentrantLock tokenLock = new ReentrantLock()
    static AtomicBoolean updating

    String data
    String content_type = "application/json"
    String method = "POST"
    String targetUrl
    int timeout = 10000

    static String getToken()
    {
        try{
            while (updating.get()){
                updating.wait()
            }
        }
        catch (InterruptedException interruptedException){
            DevCycleLogger.log("Thread waiting to retrieve toke interrupted")
            DevCycleLogger.log(interruptedException.message)
            DevCycleLogger.log_exception()
        }
        return token
    }
    static void setToken(String s)
    {
        token = s
    }

    static void acquireTokenLock()
    {
        updating.set(true)
        tokenLock.lock()
    }

    static void releaseTokenLock()
    {
        tokenLock.unlock()
        updating.set(false)
        updating.notifyAll()
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
            return response.toString()
        }
        catch (SocketTimeoutException socketTimeoutException){
            DevCycleLogger.log("Socket timeout exception occurred while sending the request")
            DevCycleLogger.log_exception(socketTimeoutException)
            throw socketTimeoutException
        }
        catch(IOException ioException) {
            DevCycleLogger.log("input-output exception occurred while sending the request")
            DevCycleLogger.log_exception(ioException)
            throw ioException
        }
        catch(Exception e) {
            DevCycleLogger.log("unknown exception occurred while sending the request")
            DevCycleLogger.log_exception(e)
            throw e
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }


}