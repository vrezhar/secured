package com.secured.data


class Document
{
    List<Products> products
    String requestType
    String releaseOrderNumber
    String documentNumber
    String turnoverType
    String tradeSenderInn
    String tradeOwnerInn
    String tradeSenderName
    String tradeOwnerName //who is owner and who is trade owner
    String receiverInn
    String receiver //not clear when this
    String owner //or this can be blank
    String ownerInn
    String tradeRecipientInn //example contains this, nothing in the documentation
    String tradeRecipient // not sure if need this
    final String pdf = "string" //?(example uses, nothing in documentation)
    long documentDate
    long transferDate
    long  acceptanceDate

    Date dateCreated
    Date lastUpdated

    static constraints = {
        products nullable: false
        requestType validator: {value, object ->
            if(value != "ACCEPTANCE" && value != "SHIPMENT")
                return false
        }
        releaseOrderNumber validator: { value, object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value == ""))
                return false
        }
        documentNumber nullable: false, blank: false, unique: true
        turnoverType validator: {value, object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
        tradeSenderInn validator: {value, object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        tradeOwnerInn validator: {value, object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        tradeRecipientInn validator: {value, object ->
            if(object?.requestType == "ACCEPTANCE" && (value == null || value ==""))
                return false
        }
        documentDate nullable: false //?
        transferDate nullable: false //?
        acceptanceDate nullable: false //?
    }
}
