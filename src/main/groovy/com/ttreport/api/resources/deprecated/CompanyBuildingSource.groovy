package com.ttreport.api.resources.deprecated


import grails.validation.Validateable

class CompanyBuildingSource implements Validateable
{

    String mainToken
    String companyToken
    String inn
    String address

    static constraints = {
        companyToken nullable: true, blank: true
        mainToken nullable: true, blank: true
        address nullable: false, blank: false
        inn nullable: false, blank: false
    }

}
