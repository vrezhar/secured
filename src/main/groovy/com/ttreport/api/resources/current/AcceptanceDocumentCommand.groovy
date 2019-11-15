package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class AcceptanceDocumentCommand extends GenericDocumentCommand
{
    String release_order_number = "test"
    long acceptance_date = new Date().toInstant().getEpochSecond()
    String trade_sender_inn = "test"
    String trade_owner_inn = null
    String trade_sender_name = "test"
    String trade_owner_name = null
    String trade_recipient_inn = null
    static constraints = {
        importFrom GenericDocumentCommand
        release_order_number nullable: false, blank: false
        trade_owner_inn nullable: true, blank: true
        trade_owner_name nullable: true, blank: true
        trade_recipient_inn nullable: true, blank: true
        trade_sender_inn nullable: false, blank: false
        trade_sender_name nullable: false, blank: false
        acceptance_date notEqual: 0L
    }

}
