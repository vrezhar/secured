package com.secured.api.resources.current

import com.secured.api.resources.current.DocumentCommand
import grails.compiler.GrailsCompileStatic

@GrailsCompileStatic
class ShipmentDocumentCommand extends DocumentCommand
{
    String pdf
    String owner
    String owner_inn
    String receiver_inn
    String receiver
    String sender_inn
    String sender

    static  constraints = {
        pdf nullable: true, blank: true
        owner nullable: false, blank: false
        owner_inn nullable: false, blank: false
        receiver nullable: false, blank: false
        receiver_inn nullable: false, blank: false
        sender nullable: false, blank: false
        sender_inn nullable: false, blank: false
    }
}
