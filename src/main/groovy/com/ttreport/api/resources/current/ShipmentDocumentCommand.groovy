package com.ttreport.api.resources.current


import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocumentCommand extends DocumentCommand
{
    String owner
    String owner_inn
    String receiver_inn
    String receiver
    String sender_inn
    String sender

    static  constraints = {
        owner nullable: false, blank: false
        owner_inn nullable: false, blank: false
        receiver nullable: false, blank: false
        receiver_inn nullable: false, blank: false
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false
    }
}
