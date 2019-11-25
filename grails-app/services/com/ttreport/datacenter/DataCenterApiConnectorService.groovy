package com.ttreport.datacenter

import com.ttreport.api.types.DocumentType
import com.ttreport.api.types.Endpoint
import grails.async.Promise
import grails.compiler.GrailsCompileStatic

import static grails.async.Promises.task
import static grails.async.Promises.waitAll
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
                    (Endpoint.INDIVIDUAL): "/api/v3/lk/documents/commissioning/indi/create"
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

    String sendDocument(Document document, DocumentType type, boolean testing = true)
    {
        String response = null
        APIHttpClient client = new APIHttpClient()
        client.targetUrl = (testing ? test_url : prod_url) + endpoint_urls[type as Endpoint]
        client.data = formPayload(document,type)
        Promise tokenChecked = task {
            if(isExpired(APIHttpClient.getToken())) {
                try{
                    APIHttpClient.setToken(retrieveToken(testing))
                    DevCycleLogger.log("token updated")
                }
                catch (Exception ignored) {
                    return false
                }
            }
            return true
        }
        Promise connectionEstablished = task {
            client.sendHttpRequest()
        }
        boolean ok = tokenChecked.get()
        tokenChecked.then {
            response = connectionEstablished.get()
        }
        connectionEstablished.then {
            try{
                Map error = new JsonSlurper().parseText(response) as Map
                if(error.error_message) {
                    response = error.error_message
                }
            }
            catch (Exception ignored){
                document.documentId = response
                document.save()
            }
        }
        return response
    }

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

    def cancelShipment(Document document)
    {
        return 200
    }
    def getFPEntryResponse(Document document)
    {
        return 200
    }
}
