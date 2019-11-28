package com.ttreport.api.factories

import com.ttreport.api.resources.current.AcceptanceDocumentCommand
import com.ttreport.api.resources.current.DocumentCommand
import com.ttreport.api.resources.current.ExtendedProductCommand
import com.ttreport.api.resources.current.ProductCommand
import com.ttreport.api.resources.current.ShipmentDocumentCommand
import com.ttreport.api.types.CommandType
import com.ttreport.api.types.DocumentType
import com.ttreport.api.types.ProductType
import com.ttreport.data.BarCode
import com.ttreport.data.Company
import com.ttreport.data.Products
import com.ttreport.data.documents.differentiated.Document
import com.ttreport.data.documents.differentiated.existing.AcceptanceDocument
import com.ttreport.data.documents.differentiated.existing.ConsumerReleaseDocument
import com.ttreport.data.documents.differentiated.existing.MarketEntranceDocument
import com.ttreport.data.documents.differentiated.existing.RFIEntranceDocument
import com.ttreport.data.documents.differentiated.existing.ShipmentDocument

class DocumentFactory
{

    private static boolean assignProducts(Document document, List<ProductCommand> products)
    {
        boolean nonempty = false
        products.each {
            if(!it.validate()){
                it.rejected = true
            }
            BarCode barCode
            if(it instanceof ExtendedProductCommand){
                barCode = ProductFactory.getBarCodeInstance(it, ProductType.EXTENDED)
            }
            else {
                barCode = ProductFactory.getBarCodeInstance(it, ProductType.SIMPLE)
            }
            if(!barCode){
                it.rejected = true
            }
        }

        return nonempty
    }
    static Document getInstance(DocumentType type, DocumentCommand cmd = null, Company company = null)
    {
        if(!type || !cmd){
            return null
        }
        switch (type){
            case DocumentType.ACCEPTANCE:
                AcceptanceDocument acceptanceDocument = new AcceptanceDocument(company: company?: Company.findByToken(cmd.companyToken), documentNumber: cmd.document_number, documentDate: cmd.document_date)
                if(cmd instanceof AcceptanceDocumentCommand){
                    AcceptanceDocumentCommand command = cmd as AcceptanceDocumentCommand
                    acceptanceDocument.releaseOrderNumber = command.release_order_number
                    acceptanceDocument.transferDate = command.transfer_date
                    acceptanceDocument.turnoverType = command.turnover_type
                    acceptanceDocument.tradeSenderName = command.trade_sender_name
                    acceptanceDocument.tradeSenderInn = command.trade_sender_inn
                    acceptanceDocument.tradeRecipientInn = command.trade_recipient_inn
                    acceptanceDocument.tradeOwnerName = command.trade_owner_name
                    acceptanceDocument.tradeOwnerInn = command.trade_owner_inn
                }
                return acceptanceDocument
            case DocumentType.SHIPMENT:
                ShipmentDocument shipmentDocument = new ShipmentDocument(company: company?: Company.findByToken(cmd.companyToken))
                if(cmd instanceof ShipmentDocumentCommand){
                    ShipmentDocumentCommand command = cmd as ShipmentDocumentCommand
                    shipmentDocument.transferDate = command.transfer_date
                    shipmentDocument.turnoverType = command.turnover_type
                    shipmentDocument.toNotParticipant = command.to_not_participant
                    shipmentDocument.withdrawalType = command.withdrawal_type
                    shipmentDocument.withdrawalDate = command.withdrawal_date
                    shipmentDocument.stateContractId = command.st_contract_id
                    shipmentDocument.receiverInn = command.receiver_inn
                    shipmentDocument.receiver = command.receiver
                    shipmentDocument.sender = command.sender
                    shipmentDocument.senderInn = command.sender
                    shipmentDocument.ownerInn = command.owner_inn
                    shipmentDocument.owner = command.owner
                }
                return shipmentDocument
            case DocumentType.ENTRANCE:
                MarketEntranceDocument marketEntranceDocument =  new MarketEntranceDocument(company: company?: Company.findByToken(cmd.companyToken))

                return marketEntranceDocument
            case DocumentType.RELEASE:
                ConsumerReleaseDocument consumerReleaseDocument = new ConsumerReleaseDocument(company: company?: Company.findByToken(cmd.companyToken))

                return consumerReleaseDocument
            case DocumentType.INDIVIDUAL:
                RFIEntranceDocument rfiEntranceDocument = new RFIEntranceDocument()

                return rfiEntranceDocument
            default:
                return new Document(company: company?: Company.findByToken(cmd.companyToken))
        }
    }

}
