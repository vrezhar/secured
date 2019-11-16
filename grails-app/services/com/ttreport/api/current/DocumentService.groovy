package com.ttreport.api.current


import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.MarketEntranceCommand
import com.ttreport.api.resources.current.ReleaseCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.data.documents.differentiated.existing.AcceptanceDocument
import com.ttreport.data.documents.differentiated.existing.ConsumerReleaseDocument
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument
import grails.gorm.transactions.Transactional

@Transactional
class DocumentService extends ProductsService
{

    AcceptanceDocument createAcceptanceDocumentMock(AcceptanceDocumentCommand cmd)
    {
        AcceptanceDocument document = new AcceptanceDocument()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        document.requestType = "ACCEPTANCE"
        document.releaseOrderNumber = cmd.release_order_number
        document.acceptanceDate = cmd.acceptance_date
        document.tradeSenderInn = cmd.trade_sender_inn
        document.tradeSenderName = cmd.trade_sender_name
        document.tradeRecipientInn = cmd.trade_recipient_inn
        return document
    }

    ShipmentDocument createShipmentDocumentMock(ShipmentDocumentCommand cmd)
    {
        ShipmentDocument document = new ShipmentDocument()
        document.documentDate = cmd.document_date
        document.transferDate = cmd.transfer_date
        document.turnoverType = cmd.turnover_type
        document.documentNumber = cmd.document_number
        document.requestType = "SHIPMENT"
        document.receiver = cmd.receiver
        document.receiverInn = cmd.receiver_inn
        document.sender = cmd.sender
        document.senderInn = cmd.sender_inn
        document.withdrawalDate = cmd.withdrawal_date
        document.withdrawalType = cmd.withdrawal_type
        document.stateContractId = cmd.st_contract_id
        document.toNotParticipant = cmd.to_not_participant
        return document
    }

    MarketEntranceDocument createMarketEntranceDocumentMock(MarketEntranceCommand cmd)
    {
        MarketEntranceDocument document = new MarketEntranceDocument()
        document.documentDate = cmd.document_date
        document.documentNumber = cmd.document_number
        document.productionDate = cmd.production_date
        if(cmd.producer_inn){
            document.producerInn = cmd.producer_inn
        }
        if(cmd.owner_inn){
            document.ownerInn = cmd.owner_inn
        }
        document.productionType = cmd.production_type
        document.docType = cmd.doc_type
        return document
    }

    ConsumerReleaseDocument createReleaseDocumentMock(ReleaseCommand cmd)
    {
        ConsumerReleaseDocument document = new ConsumerReleaseDocument()
        document.documentDate = cmd.document_date
        document.documentNumber = cmd.document_number
        document.actionDate = cmd.action_date
        document.orderDate = cmd.order_date
        document.orderNumber = cmd.order_number
        document.action = cmd.action
        document.documentType = cmd.document_type
        return document
    }
}
