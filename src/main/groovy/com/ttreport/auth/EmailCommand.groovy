package com.ttreport.auth

import grails.validation.Validateable

class EmailCommand implements Validateable
{
    String email
    static constraints = {
        email nullable: false, blank: false, email: true, validator: { String value, EmailCommand object ->
            if(!User.findByUsername(value)){
                return false
            }
        }
    }
}
