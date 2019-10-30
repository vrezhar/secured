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

    static  constraints = {
        owner nullable: false, blank: false
        owner_inn nullable: false, blank: false
        receiver nullable: false, blank: false
        receiver_inn nullable: false, blank: false
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false
    }
}
