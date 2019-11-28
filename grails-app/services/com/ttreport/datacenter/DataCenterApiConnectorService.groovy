package com.ttreport.datacenter

import com.ttreport.api.types.DocumentType
import com.ttreport.api.types.Endpoint
import grails.async.Promise
import static grails.async.Promises.task

import com.ttreport.data.documents.differentiated.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


@Transactional
class DataCenterApiConnectorService extends SigningService {

    protected final static String  prod_url = "https://ismp.crpt.ru/api/v3"
    protected final static String test_url = "https://demo.lp.crpt.tech/api/v3"

    protected final static Map<Endpoint,String> endpoint_urls =
            [
                    (Endpoint.RANDOM_DATA): "/auth/cert/key",
                    (Endpoint.TOKEN): "/auth/cert/",
                    (Endpoint.ENTRANCE): "/api/v3/lk/documents/send",
                    (Endpoint.RELEASE): " /api/v3/lk/receipt/send",
                    (Endpoint.ACCEPTANCE): "/api/v3/lk/documents/acceptance/create",
                    (Endpoint.SHIPMENT): "/api/v3/lk/documents/shipment/create",
                    (Endpoint.CANCEL_SHIPMENT): "/api/v3/lk/documents/shipment/cancel",
                    (Endpoint.INDIVIDUAL): "/api/v3/lk/documents/commissioning/indi/create",
                    (Endpoint.STATUS): "/api/v3/facade/doc/",
                    (Endpoint.FULL_INFO): "/api/v3/facade/doc/listV2"
            ]

    protected final static Map<DocumentType,String> document_types =
            [
                    (DocumentType.ENTRANCE): "LP_INTRODUCE_GOODS",
                    (DocumentType.RELEASE): "LK_RECEIPT",
                    (DocumentType.ACCEPTANCE): "LP_ACCEPT_GOODS",
                    (DocumentType.SHIPMENT): "LP_SHIP_GOODS",
                    (DocumentType.CANCEL_SHIPMENT): "LP_CANCEL_SHIPMENT",
                    (DocumentType.INDIVIDUAL): "LK_INDI_COMMISSIONING"
            ]

    protected final static List<String> accepted_statuses =
            [
                    "IN_PROGRESS", "CHECKED_OK", "CANCELLED", "ACCEPTED", "WAIT_ACCEPTANCE", "WAIT_PARTICIPANT_REGISTRATION"
            ]

    protected static String formUrl(String root, Map params = null)
    {
        StringBuilder url = new StringBuilder(root)
        if(params){
            url.append('?')
            params.each {
                url.append(it.key)
                url.append('=')
                url.append(it.value)
                url.append('&')
            }
            if(url.length() > 0) {
                url.deleteCharAt(url.length() - 1)
            }
        }
        return url.toString()
    }

    protected String formPayload(Document document, DocumentType type)
    {
        if(type == DocumentType.CANCEL_SHIPMENT){
            String cancelDocument = new JsonBuilder(
                    [
                            participant_inn: document.company?.inn,
                            shipment_number: document.documentId
                    ]
            ).toString()
            return new JsonBuilder(
                    [
                            type: document_types[type],
                            document_format: "MANUAL",
                            product_document: cancelDocument.encodeAsBase64().toString(),
                            signature: sign(cancelDocument.getBytes(),true).encodeBase64().toString()
                    ]
            ).toString()
        }
        return new JsonBuilder(
            [
                    type: document_types[type],
                    document_format: "MANUAL",
                    product_document: document.serializeAsJson().encodeAsBase64().toString(),
                    signature: sign(document.serializeAsJson().getBytes(),true).encodeBase64().toString()
            ]
        ).toString()
    }

    protected static Map<String,String> retrieveRandomData(boolean testing = true) throws RandomDataRetrievalFailureException
    {
        APIHttpClient client = new APIHttpClient()
        String url = testing ? test_url : prod_url
        client.targetUrl = url + endpoint_urls[Endpoint.RANDOM_DATA]
        client.method = "GET"
        client.content_type = ""
        String randomData
        try{
            randomData = client.sendHttpRequest()
        }
        catch (Exception e){
            RandomDataRetrievalFailureException exception = new RandomDataRetrievalFailureException(e)
            exception.log()
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

        Map body =
                [
                        uuid: randomData.uuid,
                        data: sign(randomData.data.getBytes(),false).encodeBase64().toString()
                ]
        JsonBuilder builder = new JsonBuilder(body)
        APIHttpClient client = new APIHttpClient()
        String url = testing ? test_url : prod_url
        client.targetUrl = url + endpoint_urls[Endpoint.TOKEN]
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

    protected static Map parseToken(String token)
    {
        if(!token){
            return null
        }
        return new JsonSlurper().parseText(
                new String(
                        Base64.getUrlDecoder()
                                .decode(token.split("\\.")[1])
                )
        ) as Map
    }

    protected static isExpired(String token)
    {
        if(!token){
            return true
        }
        return (new Date().toInstant().epochSecond >= (parseToken(token).exp as Long))
    }

    //TODO make a factory for this instead of individual methods

    protected static boolean updateToken(boolean testing = true)
    {
        return task ({
            if(isExpired(APIHttpClient.getToken())) {
                APIHttpClient.acquireTokenLock()
                try{
                    APIHttpClient.setToken(retrieveToken(testing))
                    DevCycleLogger.log("token updated")
                }
                catch (Exception ignored) {
                    DevCycleLogger.log("unable to update token")
                    return false
                }
                finally {
                    APIHttpClient.releaseTokenLock()
                }
            }
            return true
        }).get()
    }
    protected static Promise<String> establishConnection(APIHttpClient client)
    {
        return task {
            try {
                DevCycleLogger.log("Trying to establish connection to ${client.targetUrl}")
                client.sendHttpRequest()
            }
            catch (Exception ignored){
                DevCycleLogger.log("Failed to establish connection")
                return null
            }
        }
    }


    Map getFullInfo(Map params, boolean testing = true)
    {
        Map response = null
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = formUrl((testing ? test_url : prod_url) + endpoint_urls[Endpoint.FULL_INFO],params)
        client.method = "GET"
        boolean ok = updateToken(testing)
        Promise<String> connectionEstablished = establishConnection(client)
        if(ok && connectionEstablished){
            response = new JsonSlurper().parseText(connectionEstablished.get()) as Map
        }
        return response
    }

    Map getInfo(Document document, boolean testing = true)
    {
        Map response = null
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (testing ? test_url : prod_url) + endpoint_urls[Endpoint.STATUS] + document.documentId + "/body"
        client.method = "GET"
        boolean ok = updateToken(testing)
        Promise<String> connectionEstablished = establishConnection(client)
        if(ok && connectionEstablished){
            response = new JsonSlurper().parseText(connectionEstablished.get()) as Map
        }
        return response
    }

    Map sendDocument(Document document, DocumentType type, boolean testing = true)
    {
        if(!type){
            return null
        }
        Map response = null
        String message = null
        int status = 500
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (testing ? test_url : prod_url) + endpoint_urls[type as Endpoint]
        client.data = formPayload(document,type)
        boolean ok = updateToken(testing)
        Promise<String> connectionEstablished = establishConnection(client)
        if(ok && connectionEstablished){
            message = connectionEstablished?.get()
        }

        connectionEstablished?.then {
            try{
                response = new JsonSlurper().parseText(message) as Map
                response.status = status.toString()
            }
            catch (Exception ignored){
                document.lock()
                document.documentId = message
                document.save()
                response = getInfo(document,testing)
                if(!response){
                    return [status: 500]
                }
                document.documentStatus = response.status
                for(int retries = 5; document.documentStatus == "IN_PROGRESS" && retries; --retries){
                    try{
                        sleep(100)
                        response = getInfo(document,testing)
                        document.documentStatus = response.status
                    }
                    catch (InterruptedException e){
                        DevCycleLogger.log(e.message)
                        document.save(true)
                        return response
                    }
                }
                for(okStatus in accepted_statuses){
                    if(okStatus == document.documentStatus){
                        response.status = 200
                        document.save(true)
                        return response
                    }
                }
            }
        }
        response.status = status
        try {
            document.save(true)
        }
        catch (Exception e){
            DevCycleLogger.log("Exception occurred while trying to save the document")
            DevCycleLogger.log(e.message)
            DevCycleLogger.log("stacktrace: ")
            DevCycleLogger.log_exception(e)
        }
        return response
    }

    Map getAcceptanceResponse(Document document)
    {
        return [status: 200]
    }

    Map getShipmentResponse(Document document)
    {
        return [status: 200]
    }

    Map getReleaseResponse(Document document)
    {
        return [status: 200]
    }

    Map getMarketEntryResponse(Document document)
    {
        return [status: 200]
    }

    Map cancelShipment(Document document)
    {
        return [status: 200]
    }
    Map getFPEntryResponse(Document document)
    {
        return [status: 200]
    }
}
