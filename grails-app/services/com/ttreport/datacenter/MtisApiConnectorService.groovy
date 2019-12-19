package com.ttreport.datacenter

import com.ttreport.api.types.DocumentType
import com.ttreport.api.types.Endpoint
import grails.async.Promise
import static grails.async.Promises.task

import com.ttreport.data.documents.differentiated.Document
import com.ttreport.logs.ServerLogger
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder
import groovy.json.JsonSlurper


@Transactional
class MtisApiConnectorService extends SigningService {

    protected final static String  prod_url = "https://ismp.crpt.ru/api/v3"
    protected final static String test_url = "https://demo.lp.crpt.tech/api/v3"

    protected final static Map<Endpoint,String> endpoint_urls =
            [
                    (Endpoint.RANDOM_DATA): "/auth/cert/key",
                    (Endpoint.TOKEN): "/auth/cert/",
                    (Endpoint.ENTRANCE): "/lk/documents/send",
                    (Endpoint.RELEASE): " /lk/receipt/send",
                    (Endpoint.ACCEPTANCE): "/lk/documents/acceptance/create",
                    (Endpoint.SHIPMENT): "/lk/documents/shipment/create",
                    (Endpoint.CANCEL_SHIPMENT): "/lk/documents/shipment/cancel",
                    (Endpoint.INDIVIDUAL): "/lk/documents/commissioning/indi/create",
                    (Endpoint.STATUS): "/facade/doc/",
                    (Endpoint.FULL_INFO): "/facade/doc/listV2",
                    (Endpoint.REMAINS_DESCRIPTION): "/lk/documents/create",
                    (Endpoint.FULL_REMAINS_DESCRIPTION): "/lk/documents/create",
                    (Endpoint.REMAINS_REGISTRY): "/lk/documents/create"
            ]

    protected final static Map<DocumentType,String> document_types =
            [
                    (DocumentType.ENTRANCE): "LP_INTRODUCE_GOODS",
                    (DocumentType.RELEASE): "LK_RECEIPT",
                    (DocumentType.ACCEPTANCE): "LP_ACCEPT_GOODS",
                    (DocumentType.SHIPMENT): "LP_SHIP_GOODS",
                    (DocumentType.CANCEL_SHIPMENT): "LP_CANCEL_SHIPMENT",
                    (DocumentType.INDIVIDUAL): "LK_INDI_COMMISSIONING",
                    (DocumentType.REMAINS_REGISTRY): "LP_INTRODUCE_OST",
                    (DocumentType.REMAINS_DESCRIPTION): "OST_DESCRIPTION",
                    (DocumentType.FULL_REMAINS_DESCRIPTION): "OST_DESCRIPTION"
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

    protected static String formPayload(Document document, DocumentType type)
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
        String serialized = document.serializeAsJson()
        return new JsonBuilder(
            [
                    type: document_types[type],
                    document_format: "MANUAL",
                    product_document: serialized.encodeAsBase64().toString(),
                    signature: sign(serialized.getBytes(),true).encodeBase64().toString()
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
            randomData = client.sendRequest()
        }
        catch (Exception e){
            RandomDataRetrievalFailureException exception = new RandomDataRetrievalFailureException(e)
            exception.log()
            throw exception
        }
        ServerLogger.log("Retrieved random data")
        ServerLogger.log(randomData)
        Map response = new JsonSlurper().parseText(randomData) as Map
        ServerLogger.log("parsed, uuid: ${response.uuid}, data: ${response.data}")
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        return response
    }

    protected static String retrieveToken(boolean testing = true) throws TokenRetrievalFailureException
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
        ServerLogger.log("built token retrieval body: ${body}")
        client.data = builder.toString()
        String data
        try {
            data = client.sendRequest()
        }
        catch (Exception e)
        {
            TokenRetrievalFailureException exception = new TokenRetrievalFailureException(e)
            exception.log()
            throw exception
        }
        ServerLogger.log("retrieved data: ${data}")
        ServerLogger.print_logs()
        ServerLogger.cleanup()
        try{
            return (new JsonSlurper().parseText(data) as Map).token
        }
        catch (Exception e){
            ServerLogger.log_exception(e)
            return null
        }

    }

    protected static Map parseToken(String token)
    {
        if(!token){
            return null
        }
        try{
            return new JsonSlurper().parseText(
                    new String(
                            Base64.getUrlDecoder()
                                    .decode(token.split("\\.")[1])
                    )
            ) as Map
        }
        catch(Exception e){
            ServerLogger.log_exception(e)
            return [exp: 0]
        }

    }

    protected static isExpired(String token)
    {
        if(!token){
            return true
        }
        return (new Date().toInstant().epochSecond >= (parseToken(token).exp as Long))
    }

    static boolean updateToken(boolean testing = true)
    {
        return task ({
            APIHttpClient.acquireTokenLock()
            try {
                if (isExpired(APIHttpClient.getToken())) {
                    APIHttpClient.setToken(retrieveToken(testing))
                    ServerLogger.log("token updated")
                }
            }
            catch (Exception ignored) {
                ServerLogger.log_exception(ignored)
                ServerLogger.log("unable to update token")
                return false
            }
            finally {
                APIHttpClient.releaseTokenLock()
            }
            return true
        }).get()
    }
    protected static Promise<String> establishConnection(APIHttpClient client)
    {
        return task {
            try {
                ServerLogger.log("Trying to establish connection to ${client.targetUrl}")
                client.sendRequest()
            }
            catch (Exception ignored){
                ServerLogger.log("Failed to establish connection")
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
        Promise<String> connectionEstablished = task {
            try {
                ServerLogger.log("Trying to establish connection to ${client.targetUrl}")
                client.sendRequest()
            }
            catch (Exception ignored){
                ServerLogger.log("Failed to establish connection")
                return null
            }
        }
        if(connectionEstablished){
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
        Promise<String> connectionEstablished = task {
            try {
                ServerLogger.log("Trying to establish connection to ${client.targetUrl}")
                client.sendRequest()
            }
            catch (Exception e){
                ServerLogger.log("Failed to establish connection")
                ServerLogger.log_exception(e)
                return null
            }
        }
        String message = connectionEstablished.get()
        if(connectionEstablished && message){
            response = new JsonSlurper().parseText(message) as Map
            ServerLogger.log("retrieved document info:\n",message)
        }
        return response
    }

    Map sendDocument(Document document, DocumentType type, boolean testing = true)
    {
        String message = null
        if(!type){
            return null
        }
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (testing ? test_url : prod_url) + endpoint_urls[type as Endpoint]
        client.data = formPayload(document,type)
        boolean ok = updateToken(testing)
        Promise<String> connectionEstablished = establishConnection(client)
        if(ok && connectionEstablished){
            message = connectionEstablished.get()
            Promise<Map> checkDocument = task {
                Map response = null
                try{
                    response = new JsonSlurper().parseText(message) as Map
                    response.status = "500"
                }
                catch (Exception ignored){
                    document.documentId = message
                    response = getInfo(document,testing)
                    if(!response){
                        return response
                    }
                    document.documentStatus = response.status
                    for(int retries = 5; document.documentStatus == "IN_PROGRESS" && retries; --retries){
                        try{
                            sleep(100)
                            response = getInfo(document,testing)
                            document.documentStatus = response.status
                        }
                        catch (InterruptedException e){
                            ServerLogger.log(e.message)
                        }
                    }
                    for(okStatus in accepted_statuses){
                        if(okStatus == document.documentStatus){
                            response.status = 200
                        }
                    }
                }
                return response
            }
            Map response = checkDocument.get()
            Document.withNewSession { session ->
                try {
                    document.save(true)
                    return
                }
                catch (Exception e){
                    ServerLogger.log_exception(e)
                    return
                }
            }
            return response
        }

        return [status: document?.documentStatus]
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
