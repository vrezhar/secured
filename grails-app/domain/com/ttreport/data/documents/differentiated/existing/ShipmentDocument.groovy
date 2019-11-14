package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.GenericDocument
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocument extends GenericDocument
{

    String receiverInn
    String receiver
    String owner
    String ownerInn
    String sender
    String senderInn
    String withdrawalType
    String withdrawalDate
    String stateContractId
    boolean toNotParticipant
    String pdf = "string"

    @Override
    Map<String,Object> getAsMap()
    {
        Map<String,Object> map = super.getAsMap()
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
        requestType validator: { String value, ShipmentDocument object ->
            if(value != "SHIPMENT"){
                return false
            }
        }
        senderInn nullable: false, blank: false
        ownerInn nullable: false, blank: false
        receiverInn nullable: false, blank: false
        sender nullable: false, blank: false
        owner nullable: false, blank: false
        receiver nullable: false, blank: false
    }
}
