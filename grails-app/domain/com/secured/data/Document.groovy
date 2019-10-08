package com.secured.data

import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class Document
{
    //TODO implement comparable on Products and substitute the list with a SortedSet
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

    static hasMany = [products: Products]

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
