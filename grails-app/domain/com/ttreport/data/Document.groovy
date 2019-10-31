package com.ttreport.data

import com.ttreport.logs.DevCycleLogger
import grails.compiler.GrailsCompileStatic
import groovy.json.JsonBuilder

@GrailsCompileStatic
class Document
{
    String requestType
    String releaseOrderNumber
    String documentNumber
    String turnoverType
    String tradeSenderInn
    String tradeOwnerInn
    String tradeSenderName
    String tradeOwnerName
    String receiverInn
    String receiver
    String owner
    String ownerInn
    String tradeRecipientInn
    String sender
    String senderInn
    String pdf = "string"
    long documentDate
    long transferDate
    long acceptanceDate

    Date dateCreated
    Date lastUpdated

    static hasMany = [barCodes: BarCode]
    static belongsTo = [company: Company]

    static String serializeAsJson(Document document)
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

    static String encodeAsBase64(Document document)
    {
        return serializeAsJson(document).bytes.encodeAsBase64()
    }

    static constraints = {

        requestType validator: { String value, Document object ->
            if(value != "ACCEPTANCE" && value != "SHIPMENT")
                return false
        }
        releaseOrderNumber nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value == ""))
                return false
        }
        documentNumber nullable: false, blank: false, unique: true
        turnoverType validator: { String value, Document object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
        tradeSenderInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        tradeSenderName nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value == ""))
                return false
        }
        tradeOwnerInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        tradeOwnerName nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value == ""))
                return false
        }
        tradeRecipientInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        senderInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        ownerInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        receiverInn nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        sender nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        owner nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        receiver nullable: true, validator: { String value, Document object ->
            if(object?.requestType == "SHIPMENT" && (value == null || value ==""))
                return false
        }
        documentDate nullable: false
        transferDate nullable: false
        acceptanceDate nullable: true, validator: { long value, Document object ->
            if(object?.requestType == "ACCEPTANCE" && value == 0)
                return false
        }
    }
}
