package com.secured.user

import grails.validation.Validateable

class CompanyCommand implements Validateable
{
    String companyId
    String address

    static constraints = {
        address nullable: false, blank: false
        companyId nullable: false, blank: false
    }
}
