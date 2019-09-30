package com.secured.api.resources

import grails.validation.Validateable

class ShipmentDocumentCommand extends DocumentCommand implements Validateable
{
    String pdf = "string"
    String owner
    String owner_inn
    String receiver_inn
    String receiver
    String sender_inn
    String sender
}
