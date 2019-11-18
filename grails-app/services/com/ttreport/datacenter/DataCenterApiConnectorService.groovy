package com.ttreport.datacenter

import com.ttreport.api.resources.current.FromPhysCommand
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.RFPProductCirculationDocument
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper
import org.springframework.security.access.method.P

@Transactional
class DataCenterApiConnectorService extends SigningService {

    protected final static String  prod_url = "https://ismp.crpt.ru/api/v3/"
    protected final static String test_url = "https://demo.lp.crpt.tech/api/v3/"

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

    //TODO make a factory for this instead of individual methods

    def getAcceptanceResponse(Document document)
    {
        return 200
    }

    def getShipmentResponse(Document document)
    {
        return 200
    }

    def getReleaseResponse(Document document)
    {
        return 200
    }

    def getMarketEntryResponse(Document document)
    {
        return 200
    }

    def cancelShipment(ShipmentDocument document)
    {
        return 200
    }
    def getFPEntryResponse(Document document)
    {
        return 200
    }
}
