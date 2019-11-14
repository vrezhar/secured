package com.ttreport.data.documents.differentiated.existing

import com.ttreport.data.documents.differentiated.Document
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocument extends Document {

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
        map.pdf = this.pdf
        map.receiver = this.receiver
        map.receiver_inn = this.receiverInn
        map.owner = this.owner
        map.owner_inn = this.ownerInn
        map.sender = this.sender
        map.sender_inn = this.senderInn
        return map
    }

    static constraints = {
        importFrom Document
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
