package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocumentCommand extends GenericDocumentCommand
{
    String transfer_date = "2019"
    String turnover_type = "test"
    String release_order_number = "test"
    String acceptance_date = new Date().toInstant().toString()
    String trade_sender_inn = "test"
    String trade_sender_name = "test"
    String trade_owner_inn = "test"
    String trade_owner_name = "test"
    String trade_recipient_inn = "test"
    static constraints = {
        importFrom GenericDocumentCommand
        transfer_date nullable: false, blank: false
        turnover_type nullable: false, blank: false
        release_order_number nullable: false, blank: false
        trade_owner_inn nullable: false, blank: false
        trade_owner_name nullable: false, blank: false
        trade_recipient_inn nullable: false, blank: false
        trade_sender_inn nullable: false, blank: false
        trade_sender_name nullable: false, blank: false
        acceptance_date nullable: false, blank: false
    }

}
