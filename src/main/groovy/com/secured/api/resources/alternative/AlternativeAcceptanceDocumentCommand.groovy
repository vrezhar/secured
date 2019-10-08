package com.secured.api.resources.alternative

import grails.compiler.GrailsCompileStatic
import grails.validation.Validateable

@GrailsCompileStatic
class AlternativeAcceptanceDocumentCommand extends AlternativeDocumentCommand implements Validateable
{
    String release_order_number
    long acceptance_date = 0
    String trade_sender_inn
    String trade_owner_inn
    String trade_sender_name
    String trade_owner_name
    String trade_recipient_inn

    static constraints = {
        release_order_number nullable: false, blank: false
        trade_owner_inn nullable: false, blank: false
        trade_owner_name nullable: false, blank: false
        trade_recipient_inn nullable: false, blank: false
        trade_sender_inn nullable: false, blank: false
        trade_sender_name nullable: false, blank: false
        acceptance_date notEqual: 0
    }
}
