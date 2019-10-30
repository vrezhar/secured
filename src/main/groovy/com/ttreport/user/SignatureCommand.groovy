package com.ttreport.user

import grails.validation.Validateable

class SignatureCommand implements Validateable
{
    String body

    static constraints = {
        body nullable: false, blank: false
    }
}
