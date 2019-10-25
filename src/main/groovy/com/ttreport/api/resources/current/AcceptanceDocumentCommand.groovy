package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocumentCommand extends DocumentCommand
{
    String release_order_number = "test"
    long acceptance_date = 1L
    String trade_sender_inn = "test"
    String trade_owner_inn = "test"
    String trade_sender_name = "test"
    String trade_owner_name = "test"
    String trade_recipient_inn = "test"

    static constraints = {
        release_order_number nullable: false, blank: false
        trade_owner_inn nullable: false, blank: false
        trade_owner_name nullable: false, blank: false
        trade_recipient_inn nullable: false, blank: false
        trade_sender_inn nullable: false, blank: false
        trade_sender_name nullable: false, blank: false
        acceptance_date notEqual: 0L
    }

}
