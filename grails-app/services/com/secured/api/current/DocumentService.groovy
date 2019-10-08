package com.secured.api.current


import com.secured.api.resources.current.AcceptanceDocumentCommand
import com.secured.api.resources.current.DocumentCommand
import com.secured.api.resources.current.ShipmentDocumentCommand
import com.secured.data.Document
import grails.gorm.transactions.Transactional

@Transactional
class DocumentService extends ProductsService
{
    Document createDocumentMock(DocumentCommand cmd)
    {
        Document document = new Document()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        return document
    }

    Document createAcceptanceDocumentMock(AcceptanceDocumentCommand cmd)
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

    Document createShipmentDocumentMock(ShipmentDocumentCommand cmd)
    {
        Document document = createDocumentMock(cmd)
        document.requestType = "SHIPMENT"
        if(cmd.pdf != "" && cmd.pdf != null){
            document.pdf = cmd.pdf
        }
        document.owner = cmd.owner
        document.ownerInn = cmd.owner_inn
        document.receiver = cmd.receiver
        document.receiverInn = cmd.receiver_inn
        document.sender = cmd.sender
        document.senderInn = cmd.sender_inn
        return document
    }
}
