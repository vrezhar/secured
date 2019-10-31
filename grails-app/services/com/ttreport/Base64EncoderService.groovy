package com.ttreport

import com.ttreport.data.Document
import com.ttreport.logs.DevCycleLogger
import grails.gorm.transactions.Transactional
import groovy.json.JsonBuilder

@Transactional
class Base64EncoderService {

    String serializeAsJson(Document document)
    {
        Map map = [
                document_number: document.documentNumber,
                request_type: document.requestType,
                products: document.barCodes.collect{
                    Map collected = [:]
                    collected.tax = it.products.tax
                    collected.cost = it.products.cost
                    collected.description = it.products.description
                    if(it.uit_code){
                        collected.uit_code = it.uit_code
                    }
                    if(it.uitu_code){
                        collected.uitu_code = it.uitu_code
                    }
                    collected
                }
        ]
        if(document.requestType == "ACCEPTANCE"){

        }
        if(document.requestType == "SHIPMENT"){

        }
        def builder = new JsonBuilder(map)
        DevCycleLogger.log("The document as json, 'prettified': ${builder.toPrettyString()}")
        return builder.toString()
    }

    String encodeAsBase64(Document document)
    {
        return serializeAsJson(document).bytes.encodeAsBase64()
    }

    String decodeBase64(String input){
        return new String(input.decodeBase64())
    }
}
