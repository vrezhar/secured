package com.secured.api.resources.deprecated


import grails.validation.Validateable

class CompanyBuildingSource implements Validateable
{

    String mainToken
    String companyToken
    String companyId
    String address

    static constraints = {
        companyToken nullable: true, blank: true
        mainToken nullable: true, blank: true
        address nullable: false, blank: false
        companyId nullable: false, blank: false
    }

}
