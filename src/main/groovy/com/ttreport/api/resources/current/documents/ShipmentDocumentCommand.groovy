package com.ttreport.api.resources.current.documents


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocumentCommand extends GenericDocumentCommand
{
    String transfer_date = "2019"
    String turnover_type = "SALE"
    String owner = "test"
    String owner_inn = "test"
    String receiver_inn = "test"
    String receiver = "test"
    String sender_inn = "test"
    String sender = "test"
    String withdrawal_type = "test"
    String withdrawal_date = "test"
    String st_contract_id = "test"
    boolean to_not_participant = true

    static  constraints = {
        importFrom GenericDocumentCommand
        transfer_date nullable: false, blank: false
        turnover_type validator: { String value, ShipmentDocumentCommand object ->
            if(value != "SALE" && value != "COMMISSION" && value != "AGENT" && value != "CONTRACT")
                return false
        }
        owner nullable: true, blank: true
        owner_inn nullable: true, blank: true
        receiver nullable: true, validator: { String value, ShipmentDocumentCommand object ->
            if(!value && !object?.to_not_participant){
                return false
            }
        }
        receiver_inn nullable: true, validator: { String value, ShipmentDocumentCommand object ->
            if(!value && !object?.to_not_participant){
                return false
            }
        }
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false
        withdrawal_date nullable: false, blank: false
        withdrawal_type nullable: false, blank: false
        st_contract_id nullable: false, blank: false
    }
}
