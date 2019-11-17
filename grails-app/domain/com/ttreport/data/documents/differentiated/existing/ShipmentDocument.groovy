package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocument extends GenericDocument
{

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
        map.document_num = documentNumber
        map.pdf = pdf
        map.receiver = receiver
        map.receiver_inn = receiverInn
        map.owner = company?.name
        map.owner_inn = company?.inn
        map.sender = sender
        map.sender_inn = senderInn
        return map
    }

    static constraints = {
        importFrom GenericDocument
        requestType validator: { String value, ShipmentDocument object ->
            if(value != "SHIPMENT"){
                return false
            }
        }
        senderInn nullable: false, blank: false
        receiverInn nullable: false, blank: false
        sender nullable: false, blank: false
        receiver nullable: false, blank: false
    }
}
