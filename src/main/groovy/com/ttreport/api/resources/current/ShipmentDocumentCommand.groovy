package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocumentCommand extends DocumentCommand
{
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
        importFrom DocumentCommand
        owner nullable: false, blank: false
        owner_inn nullable: false, blank: false
        receiver nullable: false, blank: false
        receiver_inn nullable: false, blank: false
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false
        withdrawal_date nullable: false, blank: false
        withdrawal_type nullable: false, blank: false
        st_contract_id nullable: false, blank: false
    }
}
