package com.ttreport.auth

import grails.validation.Validateable

class PasswordRecoveryCommand implements Validateable
{
    String password
    String confirm

    static constraints = {
        password nullable: false, blank: false
        confirm nullable: false, blank: false
    }
}
