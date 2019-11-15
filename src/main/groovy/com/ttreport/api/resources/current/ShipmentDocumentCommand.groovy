package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocumentCommand extends GenericDocumentCommand
{
    String owner = null
    String owner_inn = null
    String receiver_inn = ""
    String receiver = ""
    String sender_inn = "test"
    String sender = "test"
    String withdrawal_type = "test"
    String withdrawal_date = "test"
    String st_contract_id = "test"
    boolean to_not_participant = true

    static  constraints = {
        importFrom GenericDocumentCommand
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
