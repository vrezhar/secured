package com.ttreport.datacentre

import com.ttreport.data.documents.differentiated.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper

@Transactional
class DataCentreApiConnectorService extends SigningService {

    protected final static String  prod_url = " https://ismp.crpt.ru/api/v3/"
    protected final static String test_url = "https://demo.fashion.crpt.ru/api/v3/"

    protected static Map<String,String> retrieveRandomData(boolean testing = true) throws RandomDataRetrievalFailureException
    {
        APIHttpClient client = new APIHttpClient()
        String url = testing ? test_url : prod_url
        client.targetUrl = url + "auth/cert/key"
        client.method = "GET"
        client.content_type = ""
        String randomData
        try{
            randomData = client.sendHttpRequest()
        }
        catch (Exception e){
            RandomDataRetrievalFailureException exception = new RandomDataRetrievalFailureException(e)
            throw exception
        }
        DevCycleLogger.log("Retrieved random data")
        DevCycleLogger.log(randomData)
        Map response = new JsonSlurper().parseText(randomData) as Map
        DevCycleLogger.log("parsed, uuid: ${response.uuid}, data: ${response.data}")
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        return response
    }

    String retrieveToken(boolean testing = true) throws TokenRetrievalFailureException
    {
        Map randomData
        try{
            randomData = retrieveRandomData(testing)
        }
        catch (RandomDataRetrievalFailureException e)
        {
            TokenRetrievalFailureException exception = new TokenRetrievalFailureException(e)
            exception.log()
            throw exception
        }

        Map body = [uuid: randomData.uuid, data: sign(randomData.data.getBytes(),false).encodeBase64().toString()]
        JsonBuilder builder = new JsonBuilder(body)
        APIHttpClient client = new APIHttpClient()
        String url = testing ? test_url : prod_url
        client.targetUrl = url + "auth/cert/"
        client.method = "POST"
        DevCycleLogger.log("built token retrieval body: ${body}")
        client.data = builder.toString()
        String data
        try {
            data = client.sendHttpRequest()
        }
        catch (Exception e)
        {
            TokenRetrievalFailureException exception = new TokenRetrievalFailureException(e)
            exception.log()
            throw exception
        }
        DevCycleLogger.log("retrieved data: ${data}")
        DevCycleLogger.print_logs()
        DevCycleLogger.cleanup()
        return (new JsonSlurper().parseText(data) as Map).token
    }

    def getAcceptanceResponse(Document document)
    {
        return 200
    }

    def getShipmentResponse(Document document)
    {
        return 200
    }

    def cancelShipment(String participantInn, String shipmentNumber)
    {
        return 200
    }
}
