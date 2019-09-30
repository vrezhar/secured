package com.secured.api.resources

import com.secured.data.Document

class AcceptanceDocumentCommand extends DocumentCommand
{
    String release_order_number
    long acceptance_date
    String trade_sender_inn
    String trade_owner_inn
    String trade_sender_name
    String trade_owner_name
    String trade_recipient_inn

    static Document createDocument(AcceptanceDocumentCommand cmd)
    {
        Document document = DocumentCommand.createMock(cmd)
        document.requestType = "ACCEPTANCE"
        document.releaseOrderNumber = cmd.release_order_number
        document.acceptanceDate = cmd.acceptance_date
        document.tradeSenderInn = cmd.trade_sender_inn
        document.tradeOwnerInn = cmd.trade_owner_inn
        document.tradeSenderName = cmd.trade_sender_name
        document.tradeOwnerName = cmd.trade_owner_name
        document.tradeRecipientInn = cmd.trade_recipient_inn
        document.save()
        return document
    }

    static constraints = {
        release_order_number nullable: false, blank: false
        trade_owner_inn nullable: false, blank: false
        trade_owner_name nullable: false, blank: false
        trade_recipient_inn nullable: false, blank: false
        trade_sender_inn nullable: false, blank: false
        trade_sender_name nullable: false, blank: false
        acceptance_date nullable: false, min: 1
    }

}
