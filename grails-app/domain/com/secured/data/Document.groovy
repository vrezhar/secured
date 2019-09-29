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
    String tradeOwnerName
    String receiverInn
    String receiver
    String owner
    String ownerInn
    String tradeRecipientInn
    String pdf = "string"
    long documentDate
    long transferDate
    long  acceptanceDate

    Date dateCreated
    Date lastUpdated

    static constraints = {
    }
}
