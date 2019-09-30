package com.secured.api.resources

import grails.validation.Validateable

class AcceptanceDocumentCommand extends DocumentCommand implements Validateable
{
    String release_order_number
    String trade_sender_inn
    String trade_owner_inn
    String trade_sender_name
    String trade_owner_name
    String trade_recipient_inn
}
