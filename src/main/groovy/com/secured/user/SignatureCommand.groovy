package com.secured.user

import grails.validation.Validateable

class SignatureCommand implements Validateable
{
    String body

    static constraints = {
        body nullable: false, blank: false
    }
}
