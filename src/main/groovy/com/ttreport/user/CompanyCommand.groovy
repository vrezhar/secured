package com.ttreport.user

import grails.validation.Validateable

class CompanyCommand implements Validateable
{
    String inn
    String address

    static constraints = {
        address nullable: false, blank: false
        inn nullable: false, blank: false
    }
}
