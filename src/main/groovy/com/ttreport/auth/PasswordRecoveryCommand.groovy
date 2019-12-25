package com.ttreport.auth

import grails.validation.Validateable

class PasswordRecoveryCommand implements Validateable
{
    String token
    String password
    String confirm

    static constraints = {
        token nullable: true, blank: true
        password nullable: false, blank: false
        confirm nullable: false, blank: false
    }
}
