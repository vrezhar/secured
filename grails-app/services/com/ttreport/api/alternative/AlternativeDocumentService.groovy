package com.ttreport.api.alternative

import com.ttreport.api.resources.alternative.AlternativeAcceptanceDocumentCommand
import com.ttreport.api.resources.alternative.AlternativeDocumentCommand
import com.ttreport.api.resources.alternative.AlternativeShipmentDocumentCommand
import com.ttreport.data.Document
import grails.gorm.transactions.Transactional

@Transactional
class AlternativeDocumentService extends AlternativeProductService
{

    Document createDocumentMock(AlternativeDocumentCommand cmd)
    {
        Document document = new Document()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        return document
    }

    Document createAcceptanceDocumentMock(AlternativeAcceptanceDocumentCommand cmd)
    {
        Document document = createDocumentMock(cmd)
        document.requestType = "ACCEPTANCE"
        document.releaseOrderNumber = cmd.release_order_number
        document.acceptanceDate = cmd.acceptance_date
        document.tradeSenderInn = cmd.trade_sender_inn
        document.tradeOwnerInn = cmd.trade_owner_inn
        document.tradeSenderName = cmd.trade_sender_name
        document.tradeOwnerName = cmd.trade_owner_name
        document.tradeRecipientInn = cmd.trade_recipient_inn
        return document
    }

    Document createShipmentDocumentMock(AlternativeShipmentDocumentCommand cmd)
    {
        Document document = createDocumentMock(cmd)
        document.requestType = "SHIPMENT"
        if(cmd.pdf != "" && cmd.pdf != null)
            document.pdf = cmd.pdf
        document.owner = cmd.owner
        document.ownerInn = cmd.owner_inn
        document.receiver = cmd.receiver
        document.receiverInn = cmd.receiver_inn
        document.sender = cmd.sender
        document.senderInn = cmd.sender_inn
        return document
    }

    def accept(AlternativeAcceptanceDocumentCommand cmd)
    {

    }

    def ship(AlternativeShipmentDocumentCommand cmd)
    {

    }

    def update(AlternativeAcceptanceDocumentCommand cmd)
    {

    }
}
