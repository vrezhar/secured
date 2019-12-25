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
    boolean hasParams = false
    Map<String,String> headers = new LinkedHashMap<String,String>()
    boolean connectingToOMS = false
    boolean useHttps =false
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

    void addHeader(String header, String info)
    {
        headers.put(header, info)
    }

    void addRequestParameter(String param, String value)
    {
        StringBuilder sb = new StringBuilder(targetUrl)
        if(!hasParams){
            hasParams = true
            sb.append('?')
            sb.append(param)
            sb.append('=')
            sb.append(value)
            targetUrl = sb.toString()
            return
        }
        sb.append('&')
        sb.append(param)
        sb.append('=')
        sb.append(value)
        targetUrl = sb.toString()
    }

    String sendRequest(String url_ = this.targetUrl, String data_ = this.data, String method = this.method, String content_type = this.content_type, int timeout = this.timeout, boolean usehttps = useHttps)
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
            headers.each {
                connection.setRequestProperty(it.key, it.value)
            }
            if(getToken() && !connectingToOMS){
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
//            println(connection.responseMessage)
            InputStream es
            BufferedReader bufferedReader
            try {
                es = connection.getErrorStream()
                bufferedReader = new BufferedReader(new InputStreamReader(es))
                StringBuilder errors = new StringBuilder()
                String line
                while ((line = bufferedReader.readLine()) != null) {
                    errors.append(line)
                    errors.append(System.lineSeparator())
                }
                ServerLogger.log("Server returned error: ", errors.toString())
                return errors.toString()
            }
            catch (Exception e){
                ServerLogger.log_exception(e)
                ServerLogger.log("exception thrown when trying to read the connection's error stream, rethrowing original exception")
                throw ioException
            }
            finally {
                if(bufferedReader){
                    bufferedReader.close()
                }
            }
        }
        catch(Exception e) {
            ServerLogger.log("unknown exception occurred while sending the request")
            ServerLogger.log_exception(e)
            InputStream es
            BufferedReader bufferedReader
            try {
                es = connection.getErrorStream()
                bufferedReader = new BufferedReader(new InputStreamReader(es))
                StringBuilder errors = new StringBuilder()
                String line
                while ((line = bufferedReader.readLine()) != null) {
                    errors.append(line)
                    errors.append(System.lineSeparator())
                }
                ServerLogger.log("Server returned error: ", errors.toString())
                return errors.toString()
            }
            catch (Exception nested){
                ServerLogger.log_exception(nested)
                ServerLogger.log("exception thrown when trying to read the connection's error stream, rethrowing original exception")
                throw e
            }
            finally {
                if(bufferedReader != null){
                    bufferedReader.close()
                }
            }
        }
        finally {
            if(connection != null){
                connection.disconnect()
            }
        }
    }


}