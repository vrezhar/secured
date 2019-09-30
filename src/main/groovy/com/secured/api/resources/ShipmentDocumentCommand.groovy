package com.secured.api.resources

import com.secured.data.Document
import grails.validation.Validateable

class ShipmentDocumentCommand extends DocumentCommand
{
    String pdf
    String owner
    String owner_inn
    String receiver_inn
    String receiver
    String sender_inn
    String sender

    static Document createDocument(ShipmentDocumentCommand cmd)
    {
        Document document = DocumentCommand.createMock(cmd)
        document.requestType = "SHIPMENT"
        if(cmd.pdf != "" && cmd.pdf != null)
            document.pdf = cmd.pdf
        document.owner = cmd.owner
        document.ownerInn = cmd.owner_inn
        document.receiver = cmd.receiver
        document.receiverInn = cmd.receiver_inn
        document.sender = cmd.sender
        document.senderInn = cmd.sender_inn
        document.save()
        return document
    }

    static  constraints = {
        pdf nullable: true, blank: true
        owner nullable: false, blank: false
        owner_inn nullable: false, blank: false
        receiver nullable: false, blank: false
        receiver_inn nullable: false, blank: false
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false

    }
}
