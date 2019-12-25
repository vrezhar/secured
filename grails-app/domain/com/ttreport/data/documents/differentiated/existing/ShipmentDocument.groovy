package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocument extends GenericDocument
{
    String turnoverType
    String transferDate
    String owner = company?.name
    String ownerInn = company?.inn
    String receiverInn
    String receiver
    String sender
    String senderInn
    String withdrawalType
    String withdrawalDate
    String stateContractId
    boolean toNotParticipant = true
    String pdf = "string"

    @Override
    transient Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
        map.remove("document_number")
        map.turnover_type = turnoverType
        map.transfer_date = transferDate
        map.request_type = "SHIPMENT"
        map.document_num = documentNumber
        map.pdf = pdf
        map.receiver = receiver
        map.receiver_inn = receiverInn
        map.owner = owner
        map.owner_inn = ownerInn
        map.sender = sender
        map.sender_inn = senderInn
        return map
    }

    static constraints = {
        importFrom GenericDocument
        transferDate nullable: false, blank: false
        turnoverType validator: { String value, GenericDocument object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
        senderInn nullable: false, blank: false
        receiverInn nullable: false, blank: false
        sender nullable: false, blank: false
        receiver nullable: false, blank: false
        ownerInn nullable: true, blank: true
        owner nullable: true, blank: true
        pdf nullable: true, blank: true
    }
}
